package com.nineplus.localhand.repository;

import com.nineplus.localhand.dto.jpa_interface.JobNearbyDto;
import com.nineplus.localhand.enum_class.JobStatus;
import com.nineplus.localhand.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Procedure("P_GET_NEARBY_JOBS")
    @Transactional
    List<JobNearbyDto> getJobsNearBy(@Param("USERID") int userId, @Param("USERLNG") Double jobLng,
                                     @Param("USERLAT") Double jobLat, @Param("SEARCH_RADIUS") int radius,
                                     @Param("LIMITATION") int limitation, @Param("UNIT") String unit,
                                     @Param("CATEGORIES") String categories, @Param("PRICEMAX") Double priceMax,
                                     @Param("PRICEMIN") Double priceMin);

    @Query(value = "SELECT COUNT(*) FROM job WHERE LOCAL_USER_ID = :userId AND (JOB_STATUS = :jobStatus1 OR JOB_STATUS = :jobStatus2)",
            nativeQuery = true)
    Integer checkIfLocalHasJobsProgresing(Long userId, Integer jobStatus1, Integer jobStatus2);

    @Query(value = "SELECT COUNT(*) FROM job WHERE JOB_ID = :jobId AND (JOB_STATUS = :jobStatus1 OR JOB_STATUS = :jobStatus2)",
            nativeQuery = true)
    Integer checkIfJobsProgresing(Long jobId, Integer jobStatus1, Integer jobStatus2);
}
