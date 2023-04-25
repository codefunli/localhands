package com.nineplus.localhand.service.impl;

import com.nineplus.localhand.model.CommonCountry;
import com.nineplus.localhand.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<CommonCountry> getAllCountry(){
        List<CommonCountry> countryList = countryRepository.findAll();
        return countryList;
    }
}
