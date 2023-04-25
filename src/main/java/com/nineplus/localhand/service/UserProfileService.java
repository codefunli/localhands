package com.nineplus.localhand.service;

import com.nineplus.localhand.dto.UserUpdateDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.UserProfile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserProfileService {

    UserProfile findByUserId(Long userId);

    UserUpdateDto updateLicense(UserUpdateDto userUpdateDto, List<MultipartFile> driverLicense) throws LocalHandException;

    UserUpdateDto updateCategory(UserUpdateDto userUpdateDto) throws LocalHandException;

    UserUpdateDto updatePayment(UserUpdateDto userUpdateDto) throws LocalHandException;

    UserUpdateDto updateInfo(UserUpdateDto userUpdateDto, MultipartFile avatar) throws LocalHandException;
}
