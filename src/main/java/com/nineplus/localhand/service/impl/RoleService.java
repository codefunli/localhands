package com.nineplus.localhand.service.impl;

import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.Role;
import com.nineplus.localhand.repository.RoleRepository;
import com.nineplus.localhand.utils.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() throws LocalHandException {
        try{
            return roleRepository.findAll();
        } catch (Exception e){
            throw new LocalHandException(CommonConstants.MessageError.ER012, null);
        }
    }
}
