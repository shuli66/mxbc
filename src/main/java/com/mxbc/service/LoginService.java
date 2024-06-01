package com.mxbc.service;

import com.mxbc.Entity.EmployeeEntity;
import com.mxbc.mapper.EmployeeMapper;
import com.mxbc.utils.TokenUtils;
import com.mxbc.expection.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private EmployeeMapper employeeMapper;
    //验证账户是否合法
    public EmployeeEntity login(EmployeeEntity user) {
        EmployeeEntity userDB =employeeMapper.selectByName(user.getUserName());
        if (userDB == null){
            //抛异常
            throw new ServiceException("账户不存在");
        }
        if(! user.getPasswordHash().equals(userDB.getPasswordHash())){
            throw new ServiceException("用户名或密码错误");
        }
        //生成token
        String token = TokenUtils.createToken(userDB.getEmployeeId().toString(),userDB.getPasswordHash());
        userDB.setToken(token);
        return userDB;
    }

    public EmployeeEntity register(EmployeeEntity user) {
        EmployeeEntity userDB =employeeMapper.selectByName(user.getUserName());
        if (userDB != null){
            //抛异常
            throw new com.mxbc.expection.ServiceException("用户名已存在");
        }
        user.setUserName(user.getUserName());
        employeeMapper.insertEmployee(user);
        return user;
    }
}
