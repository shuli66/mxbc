package com.mxbc.service;

import com.mxbc.Entity.UserEntity;
import com.mxbc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService {
    @Autowired
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
        // 获取当前时间
        Timestamp date = new Timestamp(System.currentTimeMillis());
        // 设置更新时间
        userEntity.setCreatedAt(date);
        return userMapper.updateUser(userEntity);
    }

    public List<UserEntity> searchUser(String name) {
        return userMapper.searchUser(name);
    }
}
