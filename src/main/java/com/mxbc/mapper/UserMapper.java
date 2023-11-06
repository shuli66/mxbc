package com.mxbc.mapper;

import com.mxbc.Entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    @Insert("INSERT INTO user (username, password_hash, email, phone_number, created_at, user_type) " +
            "VALUES (#{userName}, #{passwordHash}, #{email}, #{phoneNumber}, #{createdAt}, #{userType})")
    int insertUser(UserEntity userEntity);

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    UserEntity getUserById(Integer userId);

    @Select("SELECT * FROM user")
    List<UserEntity> getUserAll();

    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    int deleteUser(Integer userId);

    @Update("UPDATE user SET username=#{userName}, password_hash=#{passwordHash}, email=#{email}, phone_number=#{phoneNumber}, user_type=#{userType}, created_at=#{createdAt} " +
            "WHERE user_id = #{userId}")
    int updateUser(UserEntity userEntity);
}

