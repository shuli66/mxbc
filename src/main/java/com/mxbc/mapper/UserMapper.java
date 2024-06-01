package com.mxbc.mapper;

import com.mxbc.Entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (username, password_hash, email, phone_number, created_at, user_type, avatar) " +
            "VALUES (#{userName}, #{passwordHash}, #{email}, #{phoneNumber}, #{createdAt}, #{userType}, #{avatar})")
    int insertUser(UserEntity userEntity);


    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    UserEntity getUserById(Integer userId);

    @Select("SELECT * FROM user")
    List<UserEntity> getUserAll();

    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    int deleteUser(Integer userId);

//    @Update("UPDATE user SET username=#{userName}, password_hash=#{passwordHash}, email=#{email}, phone_number=#{phoneNumber}, user_type=#{userType}, created_at=#{createdAt} " +
//            "WHERE user_id = #{userId}")
//    int updateUser(UserEntity userEntity);

    @Update("<script>" +
            "UPDATE user" +
            "<set>" +
            "<if test='userName != null'>username=#{userName},</if>" +
            "<if test='passwordHash != null'>password_hash=#{passwordHash},</if>" +
            "<if test='email != null'>email=#{email},</if>" +
            "<if test='phoneNumber != null'>phone_number=#{phoneNumber},</if>" +
            "<if test='userType != null'>user_type=#{userType},</if>" +
            "<if test='createdAt != null'>created_at=#{createdAt},</if>" +
            "<if test='avatar != null'>avatar=#{avatar},</if>" +
            "</set>" +
            "WHERE user_id = #{userId}" +
            "</script>")
    int updateUser(UserEntity userEntity);


    @Select("<script>" +
            "SELECT * FROM user" +
            " WHERE 1=1" +
            "<if test='name != null and name != \"\"'>" +
            " AND username LIKE CONCAT('%', #{name}, '%')" +
            "</if>" +
            "</script>")
    List<UserEntity> searchUser(@Param("name") String name);
}

