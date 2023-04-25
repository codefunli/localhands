package com.nineplus.localhand.service.impl;

import com.nineplus.localhand.dto.AdminResetReq;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.User;
import com.nineplus.localhand.model.UserProfile;
import com.nineplus.localhand.model.UserRole;
import com.nineplus.localhand.repository.UserProfileRepository;
import com.nineplus.localhand.repository.UserRepository;
import com.nineplus.localhand.repository.UserRoleRepository;
import com.nineplus.localhand.service.AdminServices;
import com.nineplus.localhand.service.UserService;
import com.nineplus.localhand.utils.CommonConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AdminServicesImpl implements AdminServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserDto resetUserPassword(AdminResetReq adminResetReq) throws LocalHandException {
        try{
            if (ObjectUtils.isEmpty(adminResetReq)){
                throw new LocalHandException(CommonConstants.MessageError.ER015, null);
            }
            User user = userRepository.findById(adminResetReq.getUserId()).orElse(null);
            if (ObjectUtils.isEmpty(user)){
                throw new LocalHandException(CommonConstants.MessageError.ER021, null);
            }
            if (adminResetReq.getNewPassword().isBlank()){
                throw new LocalHandException(CommonConstants.MessageError.ER003, null);
            }
            UserDto userDto = userService.updatePassword(user, adminResetReq.getNewPassword());
            return userDto;
        } catch (LocalHandException e){
            throw new LocalHandException(e.getMsgCode(), null);
        }
    }

    @Override
    public UserDto deleteUser(Long id) throws LocalHandException {
        try{
            User user = userRepository.findById(id).orElse(null);
            if (ObjectUtils.isEmpty(user)){
                throw new LocalHandException(CommonConstants.MessageError.ER025, null);
            }
            UserProfile userProfile = userProfileRepository.findUserProfileByUserId(id);
            if (ObjectUtils.isNotEmpty(userProfile)){
                userProfileRepository.delete(userProfile);
            }
            List<UserRole> roleList = userRoleRepository.findAllByUserId(id);
            if (ObjectUtils.isNotEmpty(roleList)){
                userRoleRepository.deleteAllByUserId(id);
            }
            userRepository.delete(user);
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(user, dto);
            dto.setPassword(null);
            return dto;
        }catch (LocalHandException e){
            throw new LocalHandException(e.getMsgCode(), null);
        }
    }
}
