package com.nineplus.localhand.repository;

import com.nineplus.localhand.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findUserProfileByUserId(Long userId);
}
