package com.nineplus.localhand.repository;

import com.nineplus.localhand.model.CommonCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CommonCountry, String> {

}
