package com.nineplus.localhand.service.impl;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.nineplus.localhand.dto.AuthenDto;
import com.nineplus.localhand.dto.UserAuthDetected;
import com.nineplus.localhand.dto.UserDetailDto;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.enum_class.Status;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.Role;
import com.nineplus.localhand.model.User;
import com.nineplus.localhand.model.UserProfile;
import com.nineplus.localhand.model.UserRole;
import com.nineplus.localhand.repository.RoleRepository;
import com.nineplus.localhand.repository.UserProfileRepository;
import com.nineplus.localhand.repository.UserRepository;
import com.nineplus.localhand.repository.UserRoleRepository;
import com.nineplus.localhand.service.ISftpFileService;
import com.nineplus.localhand.service.UserService;
import com.nineplus.localhand.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.util.Pair;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ISftpFileService sftpFileService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private HttpServletRequest request;

    private UserAuthUtils userAuthUtils;

    private static final String SEPARATOR = "<>";

    @Autowired
    public UserServiceImpl(@Lazy UserAuthUtils userAuthUtils) {
        this.userAuthUtils = userAuthUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUsersByUsernameOrEmailOrPhone(username, username,username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        if(user.get().getBanned()) {
            throw new UsernameNotFoundException("User has been banned");
        }
        List<Role> userRoles = roleRepository.getRolesUser(user.get().getId());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userRoles.forEach(userRole -> authorities.add(new SimpleGrantedAuthority(userRole.getRoleName())));
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), authorities);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUsersByUsername(username).orElse(new User());
    }

    @Override
    public UserDto validationAndSendOtpUser(UserDto userDto) throws LocalHandException {
        try{
            if (isUserExist(userDto)) {
                throw new LocalHandException(CommonConstants.MessageError.ER001, null);
            }
            createOTP(userDto);
            return userDto;
        } catch (LocalHandException e){
            throw new LocalHandException(e.getMsgCode(), null);
        }
    }

    private void createOTP(UserDto userDto) throws LocalHandException{
        try{
            String child = ObjectUtils.isEmpty(userDto.getUsername()) ? userDto.getPhone() : userDto.getUsername();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getReference(CommonConstants.OTPs.OTP).child(child);
            String otp = OtpUtils.generateRandomNumber();
            if (!mailService.sendVerifiedEmail(userDto, "Verification Email", otp)){
                throw new LocalHandException(CommonConstants.MessageError.ER014, null);
            }
            databaseReference.setValue(otp, (databaseError, reference) -> {
                OtpUtils.startDataDeletionTimer(databaseReference);
                System.out.println("Send OTP to phone number: " + userDto.getPhone());
                log.info("Send OTP to phone number: " + userDto.getPhone());
            });
        }catch (LocalHandException e){
            throw new LocalHandException(e.getMsgCode(), null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthenDto sendOtp(UserDto userDto) throws LocalHandException {
        try {
            if (isUserExist(userDto)) {
                throw new LocalHandException(CommonConstants.MessageError.ER001, null);
            }
            if (!firebaseService.sendOtp(userDto)) {
                throw new LocalHandException(CommonConstants.MessageError.ER016, null);
            }
            AuthenDto res = null;
            User user = this.userRepository.save(convertDtoToEntity(userDto));
            Role role = roleRepository.findByRoleName("USER");
            UserRole userRole = new UserRole();
            userRole.setRoleId(ObjectUtils.isNotEmpty(role) ? role.getId() : 1);
            userRole.setUserId(user.getId());
            userRoleRepository.save(userRole);
            if (!ObjectUtils.isEmpty(user)) {
                if (!this.mailService.sendEmail(user, "Registration success")) {
                    throw new LocalHandException(CommonConstants.MessageError.ER014, null);
                }
                userDto.setId(user.getId());
                res = getToken(user, userDto);
            }
            return res;
        } catch (LocalHandException e) {
            throw new LocalHandException(e.getMsgCode(), null);
        } catch (Exception e) {
            throw new LocalHandException(CommonConstants.MessageError.ER016, null);
        }
    }

    private User convertDtoToEntity(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        if (StringUtils.isEmpty(user.getUsername())){
            user.setUsername(user.getEmail());
        }
        user.setSysCreatedAt(instant);
        user.setSysUpdatedAt(instant);
        user.setLastIp("This is last ip");;
        user.setStatus(Status.INCOMPLETE);
        return user;
    }

    public boolean isUserExist(UserDto userDto) {
        return userRepository.checkUserExist(userDto.getUsername(), userDto.getEmail(), userDto.getPhone()) > 0;
    }


    @Override
    public UserDto updatePassword(User currentUser, String newPassword) throws LocalHandException {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(newPassword);
            currentUser.setPassword(encodedPassword);
            UserDto dto = new UserDto();
            dto.setId(currentUser.getId());
            dto.setFirstName(currentUser.getFirstName());
            dto.setLastName(currentUser.getLastName());
            dto.setEmail(currentUser.getEmail());
            dto.setUsername(currentUser.getUsername());
            dto.setPhone(currentUser.getPhone());
            userRepository.save(currentUser);
            return dto;
        } catch (Exception e) {
            throw new LocalHandException(CommonConstants.MessageError.ER001, null);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findUsersByUsernameOrEmailOrPhone(email, email,email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

//    @Override
//    public void updateUserLocation(LocationMessage locationMessage) {
//        userRepository.updateUserLocation(locationMessage.getLat(), locationMessage.getLng(), Long.parseLong(locationMessage.getSenderId()));
//    }


    @Override
    public UserDetailDto getUserDetail(Long userId) throws LocalHandException {
        try{
            Pair<Session, ChannelSftp>  sftpConnection = sftpFileService.getConnection();
            User user = userRepository.findById(userId).orElse(null);
            UserProfile userProfile = userProfileRepository.findUserProfileByUserId(userId);
            if (ObjectUtils.isEmpty(user) || ObjectUtils.isEmpty(userProfile)){
                throw new LocalHandException(CommonConstants.MessageError.ER021, null);
            }
            UserDetailDto userDetailDto = convertToCommonInfo(user, userProfile);
            userDetailDto.setAvatar(sftpFileService.getFile(user.getAvatar(), sftpConnection));
            return userDetailDto;
        } catch (LocalHandException e){
            throw new LocalHandException(e.getMsgCode(), null);
        }
    }

    @Override
    public UserDetailDto getMyInfo() throws LocalHandException {
        try{
            Pair<Session, ChannelSftp>  sftpConnection = sftpFileService.getConnection();
            UserAuthDetected userAuthRoleReq = userAuthUtils.getUserInfoFromReq();
            String username = userAuthRoleReq.getUsername();
            User user = this.getUserByUsername(username);
            UserProfile userProfile = userProfileRepository.findUserProfileByUserId(user.getId());
            if (ObjectUtils.isEmpty(user) || ObjectUtils.isEmpty(userProfile)){
                throw new LocalHandException(CommonConstants.MessageError.ER021, null);
            }
            UserDetailDto userDetailDto = new UserDetailDto();
            BeanUtils.copyProperties(user, userDetailDto);
            BeanUtils.copyProperties(userProfile, userDetailDto);
            userDetailDto.setAvatar(sftpFileService.getFile(user.getAvatar(), sftpConnection));
            if (ObjectUtils.isNotEmpty(userProfile.getHandlerMediaDriverlicense())){
                String[] pathList = userProfile.getHandlerMediaDriverlicense().split(SEPARATOR);
                Map<Integer, byte[]> imageMap = new HashMap<>();
                if (ObjectUtils.isNotEmpty(pathList)){
                    for (int i = 0; i < pathList.length; i++) {
                        imageMap.put(i, sftpFileService.getFile(pathList[i], sftpConnection));
                    }
                }
                userDetailDto.setDriverLicenseFront(imageMap.getOrDefault(0, null));
                userDetailDto.setDriverLicenseBack(imageMap.getOrDefault(1, null));
            }
            return userDetailDto;
        } catch (LocalHandException e){
            throw new LocalHandException(e.getMsgCode(), null);
        }
    }

    public UserDetailDto convertToCommonInfo(User user, UserProfile userProfile){
        UserDetailDto userDetailDto = new UserDetailDto();
        userDetailDto.setEmail(user.getEmail());
        userDetailDto.setPhone(user.getPhone());
        userDetailDto.setFirstName(user.getFirstName());
        userDetailDto.setLastName(user.getLastName());
        userDetailDto.setGender(user.getGender());
        userDetailDto.setDob(user.getDob());
        userDetailDto.setBio(userProfile.getBio());
        userDetailDto.setHandlerTypeVehicle(userProfile.getHandlerTypeVehicle());
        userDetailDto.setHomeCity(userProfile.getHomeCity());
        userDetailDto.setHomeState(userProfile.getHomeState());
        userDetailDto.setHomeCountry(userProfile.getHomeCountry());
        return userDetailDto;
    }

    public AuthenDto getToken(User createdUser, UserDto userDto) throws LocalHandException{
        try {
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) this.loadUserByUsername(createdUser.getEmail());
            String accessToken = tokenUtils.generateAccessToken(request, user);
            String refreshToken = tokenUtils.generateRefreshToken(request,user);
            userDto.setPassword(null);
            AuthenDto authenDto = new AuthenDto(accessToken,refreshToken,"Bearer",userDto);
            return authenDto;
        } catch (Exception e){
            throw new LocalHandException(CommonConstants.MessageError.ER012, null);
        }
    }

}
