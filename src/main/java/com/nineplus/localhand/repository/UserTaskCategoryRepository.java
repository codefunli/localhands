package com.nineplus.localhand.repository;

import com.nineplus.localhand.model.UserTaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserTaskCategoryRepository extends JpaRepository<UserTaskCategory, Integer> {
    List<UserTaskCategory> findAllByUserId(Long userId);

    @Transactional
    @Modifying
    void deleteAllByUserId(Long userId);
}
