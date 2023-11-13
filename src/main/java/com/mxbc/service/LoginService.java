package com.mxbc.service;

import com.mxbc.Entity.EmployeeEntity;
import com.mxbc.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired(required = false)
    private LoginMapper loginMapper;

    public EmployeeEntity findByUsernameAndPassword(String userName, String passwordHash) {
        return loginMapper.findByUsernameAndPassword(userName, passwordHash);
    }
}
