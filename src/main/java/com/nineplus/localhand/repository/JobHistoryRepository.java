package com.nineplus.localhand.repository;

import com.nineplus.localhand.model.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobHistoryRepository extends JpaRepository<JobHistory, Long> {
    @Query(value = "SELECT * from job_history where JOB_ID = :jobId and LOCAL_ID = :localId and (HELPER_ID = :helperId OR HELPER_ID IS NULL )and JOB_STATUS = :jobStatus", nativeQuery = true)
    JobHistory getHistoryToUpdate(@Param("jobId") Long jobId, @Param("localId") Long localId, @Param("helperId") Long helperId, @Param("jobStatus") Integer jobStatus);
}
