package com.mxbc.mapper;

import com.mxbc.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Insert("INSERT INTO user (username, password_hash, email, phone_number, created_at, user_type) " +
            "VALUES (#{userName}, #{password}, #{email}, #{phoneNumber}, #{createdDate}, #{type})")
    int insertUser(UserEntity userEntity);

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    UserEntity getUserById(Integer userId);

    @Select("SELECT * FROM user")
    List<UserEntity> getUserAll();

    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    int deleteUser(Integer userId);

    @Update("UPDATE user SET username=#{userName}, password_hash=#{password}, email=#{email}, phone_number=#{phoneNumber}, user_type=#{type}, created_at=#{createdDate} " +
            "WHERE user_id = #{userId}")
    int updateUser(UserEntity userEntity);
}

