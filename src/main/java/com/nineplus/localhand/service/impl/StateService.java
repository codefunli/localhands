package com.nineplus.localhand.service.impl;

import com.nineplus.localhand.model.CommonState;
import com.nineplus.localhand.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public List<CommonState> getAllState(){
        List<CommonState> stateList = stateRepository.findAll();
        return stateList;
    }
}
