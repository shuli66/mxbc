package com.mxbc.mapper;

import com.mxbc.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    @Insert("INSERT INTO `mxbc`.`user` (`username`, `password`, `email`, `phone_number`, `registration_date`) " +
            "VALUES (#{userName}, #{password}, #{email}, #{phone}, #{registrationDate})")
    int insertUser(UserEntity userEntity);

    @Select("SELECT * FROM user WHERE user_id = #{id}")
    UserEntity getUserById(Integer id);

    @Select("SELECT * FROM user")
    List<UserEntity> getUserAll();

    @Delete("DELETE FROM user WHERE user_id = #{id}")
    int deleteUser(Integer id);

    @Update("UPDATE user SET username=#{userName}, password=#{password}, email=#{email}, phone_number=#{phone}, registration_date=#{registrationDate} " +
            "WHERE user_id = #{id}")
    int updateUser(UserEntity userEntity);
}

