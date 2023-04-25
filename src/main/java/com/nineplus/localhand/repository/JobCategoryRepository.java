package com.nineplus.localhand.repository;

import com.nineplus.localhand.model.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {

    List<JobCategory> findAll();

}
