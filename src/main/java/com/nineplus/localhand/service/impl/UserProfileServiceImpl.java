package com.nineplus.localhand.service.impl;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.dto.UserUpdateDto;
import com.nineplus.localhand.enum_class.Status;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.User;
import com.nineplus.localhand.model.UserProfile;
import com.nineplus.localhand.model.UserRole;
import com.nineplus.localhand.model.UserTaskCategory;
import com.nineplus.localhand.repository.UserProfileRepository;
import com.nineplus.localhand.repository.UserRepository;
import com.nineplus.localhand.repository.UserRoleRepository;
import com.nineplus.localhand.repository.UserTaskCategoryRepository;
import com.nineplus.localhand.service.ISftpFileService;
import com.nineplus.localhand.service.UserProfileService;
import com.nineplus.localhand.utils.CommonConstants;
import com.nineplus.localhand.utils.DateTimeUtils;
import com.nineplus.localhand.utils.FirebaseService;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ISftpFileService sftpFileService;

    @Autowired
    private JobCategoryService jobCategoryService;

    @Autowired
    private UserTaskCategoryRepository userTaskCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private DateTimeUtils dateTimeUtils;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String SEPARATOR = "<>";

    @Override
    public UserProfile findByUserId(Long userId) {
        return userProfileRepository.findUserProfileByUserId(userId);
    }

    @Override
    @Transactional
    public UserUpdateDto updateLicense(UserUpdateDto userUpdateDto,
                                     List<MultipartFile> driverLicense) throws LocalHandException {
        Pair<Session, ChannelSftp> sftpConnection = null;
        Session session = null;
        ChannelSftp channel = null;
        try{
            sftpConnection = sftpFileService.getConnection();
            session = sftpConnection.getFirst();
            channel = sftpConnection.getSecond();

            User currentUser = checkUser(userUpdateDto);
            UserProfile userProfile = checkUserProfile(userUpdateDto);
            if (ObjectUtils.isNotEmpty(userProfile.getHandlerMediaDriverlicense())){
                List<String> pathList = Arrays.stream(userProfile.getHandlerMediaDriverlicense().split(SEPARATOR)).toList();
                if (ObjectUtils.isNotEmpty(pathList)){
                    for (String path : pathList) {
                        sftpFileService.removeFile(path, sftpConnection);
                    }
                }
            }
            if (ObjectUtils.isNotEmpty(driverLicense) && !driverLicense.isEmpty()){
                StringBuilder finalPath = new StringBuilder("");
                for (MultipartFile file: driverLicense) {
                    if (!sftpFileService.isValidFile(file)){
                        throw new LocalHandException(CommonConstants.MessageError.ER018, null);
                    }
                    String path = sftpFileService.uploadLicense(sftpConnection, file, userUpdateDto.getUserId());
                    finalPath.append(path).append(SEPARATOR);
                }
                userProfile.setHandlerMediaDriverlicense(finalPath.toString());
                userProfile.setHandlerAllowBackgroundcheck(userUpdateDto.getHandlerAllowBackgroundcheck());
                userProfile.setSysCreatedAt(dateTimeUtils.getNowInstant());
                userProfile.setSysUpdatedAt(dateTimeUtils.getNowInstant());
                UserRole userRole = UserRole.builder()
                        .userId(userUpdateDto.getUserId())
                        .roleId(userUpdateDto.getRole())
                        .build();

                if (!(Status.ACTIVATED.equals(currentUser.getStatus())
                        || Status.REJECTED.equals(currentUser.getStatus()))){
                    currentUser.setStatus(Status.IN_REVIEW);
                }
                userRoleRepository.save(userRole);
                userProfileRepository.save(userProfile);
                userRepository.save(currentUser);
            }
            return userUpdateDto;
        }catch (LocalHandException e){
            sftpFileService.disconnect(session,channel);
            throw new LocalHandException(e.getMsgCode(), null);
        } finally {
            sftpFileService.disconnect(session,channel);
        }
    }

    @Override
    public UserUpdateDto updateCategory(UserUpdateDto userUpdateDto) throws LocalHandException {
        try{
            User currentUser = checkUser(userUpdateDto);

            if (ObjectUtils.isNotEmpty(userUpdateDto.getJobCategory()) && ObjectUtils.isNotEmpty(currentUser)){
                List<UserTaskCategory> userTaskCategories = new ArrayList<>();
                userUpdateDto.getJobCategory().forEach(id -> {
                    UserTaskCategory userTaskCategory = new UserTaskCategory();
                    userTaskCategory.setUserId(userUpdateDto.getUserId());
                    userTaskCategory.setCategoryId(id);
                    userTaskCategories.add(userTaskCategory);
                });
                userTaskCategoryRepository.deleteAllByUserId(userUpdateDto.getUserId());
                userTaskCategoryRepository.saveAll(userTaskCategories);
            }
            return userUpdateDto;
        } catch (LocalHandException e){
            throw new LocalHandException(e.getMsgCode(), null);
        }
    }

    @Override
    public UserUpdateDto updatePayment(UserUpdateDto userUpdateDto) throws LocalHandException {
        try{
            User currentUser = checkUser(userUpdateDto);
            UserProfile userProfile = checkUserProfile(userUpdateDto);
            modelMapper.map(userUpdateDto, userProfile);
            userProfile.setSysCreatedAt(dateTimeUtils.getNowInstant());
            userProfile.setSysUpdatedAt(dateTimeUtils.getNowInstant());

            userProfileRepository.save(userProfile);

            return userUpdateDto;
        } catch (LocalHandException e){
            throw new LocalHandException(e.getMsgCode(), null);
        }
    }

    @Override
    @Transactional
    public UserUpdateDto updateInfo(UserUpdateDto userUpdateDto, MultipartFile avatar) throws LocalHandException {
        Pair<Session, ChannelSftp> sftpConnection = null;
        Session session = null;
        ChannelSftp channel = null;

        try{
            sftpConnection = sftpFileService.getConnection();
            session = sftpConnection.getFirst();
            channel = sftpConnection.getSecond();

            User currentUser = checkUser(userUpdateDto);

            UserProfile userProfile = checkUserProfile(userUpdateDto);
            if (ObjectUtils.isNotEmpty(currentUser.getAvatar())){
                sftpFileService.removeFile(currentUser.getAvatar(), sftpConnection);
            }

            if (ObjectUtils.isNotEmpty(avatar) && !avatar.isEmpty()){
                String path = sftpFileService.uploadAvatar(sftpConnection, avatar, userUpdateDto.getUserId());
                currentUser.setAvatar(path);
            }
            modelMapper.map(userUpdateDto, userProfile);
            userProfile.setSysCreatedAt(dateTimeUtils.getNowInstant());
            userProfile.setSysUpdatedAt(dateTimeUtils.getNowInstant());

            //verify new phone number
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userUpdateDto, userDto);
            if (ObjectUtils.isNotEmpty(userUpdateDto.getOtp())){
                firebaseService.sendOtp(userDto);
            }
            if (!(Status.ACTIVATED.equals(currentUser.getStatus())
                    || Status.REJECTED.equals(currentUser.getStatus()))){
                currentUser.setStatus(Status.IN_REVIEW);
            }
            userProfileRepository.save(userProfile);
            userRepository.save(currentUser);
            return userUpdateDto;
        } catch (LocalHandException e){
            throw new LocalHandException(e.getMsgCode(), null);
        } catch (ExecutionException | InterruptedException e){
            throw new LocalHandException(CommonConstants.MessageError.ER016, null);
        } finally {
            sftpFileService.disconnect(session,channel);
        }
    }

    public UserProfile checkUserProfile(UserUpdateDto userUpdateDto) throws LocalHandException{
        if (ObjectUtils.isEmpty(userUpdateDto)){
            throw new LocalHandException(CommonConstants.MessageError.ER003, null);
        }
        UserProfile userProfile = this.findByUserId(userUpdateDto.getUserId());
        if (ObjectUtils.isEmpty(userProfile)){
            userProfile = new UserProfile();
            userProfile.setUserId(userUpdateDto.getUserId());
        }
        return userProfile;
    }

    public User checkUser(UserUpdateDto userUpdateDto) throws LocalHandException{
        if (ObjectUtils.isEmpty(userUpdateDto)){
            throw new LocalHandException(CommonConstants.MessageError.ER003, null);
        }
        User currentUser = userRepository.findById(userUpdateDto.getUserId()).orElse(null);
        if (ObjectUtils.isEmpty(currentUser)){
            throw new LocalHandException(CommonConstants.MessageError.ER021, null);
        }
        return currentUser;
    }
}
