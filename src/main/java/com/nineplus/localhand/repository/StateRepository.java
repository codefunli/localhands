package com.nineplus.localhand.repository;

import com.nineplus.localhand.model.CommonState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<CommonState, Integer> {
}
