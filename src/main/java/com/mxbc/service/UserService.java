package com.mxbc.service;

import com.mxbc.entity.UserEntity;
import com.mxbc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    public int insertUser(UserEntity userEntity) {
        return userMapper.insertUser(userEntity);
    }

    public UserEntity getUserById(Integer id) {
        return userMapper.getUserById(id);
    }
    public List<UserEntity> getUserAll(){
        return userMapper.getUserAll();
    }
    public int deleteUser(Integer id){
        return userMapper.deleteUser(id);
    }
    public int updateUser(UserEntity userEntity){
        return userMapper.updateUser(userEntity);
    }
}
