package com.mxbc.mapper;

import com.mxbc.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Insert("INSERT INTO `mxbc`.`user` (`username`, `password`, `email`, `phone_number`, `registration_date`) " +
            "VALUES (#{userName}, #{password}, #{email}, #{phoneNumber}, #{registrationDate})")
    int insertUser(UserEntity userEntity);

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    UserEntity getUserById(Integer userId);

    @Select("SELECT * FROM user")
    List<UserEntity> getUserAll();

    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    int deleteUser(Integer userId);

    @Update("UPDATE user SET username=#{userName}, password=#{password}, email=#{email}, phone_number=#{phoneNumber}, registration_date=#{registrationDate} " +
            "WHERE user_id = #{userId}")
    int updateUser(UserEntity userEntity);
}

