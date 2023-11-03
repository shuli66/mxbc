package com.mxbc.mapper;

import com.mxbc.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {

    @Insert("INSERT INTO `mxbc`.`user` (`user_id`, `username`, `password`, `email`, `phone_number`, `registration_date`) VALUES ('null', '#{username}', '#{password}', '#{email}', '#{phone_number}', '#{registration_date}');")
    int insertUser(UserEntity userEntity);

    @Select("select * from user where user_id='#{user_id}'")
    int getUserId(Integer id);

    @Select("select * from user")
    UserEntity getUser();

    @Delete("delete * from user where user_id='#{user_id}'")
    int deleteUser(Integer id);

    @Update("update * set username='#{username}', password='#{password}', email='#{email}', phone_number='#{phone_number}', registration_date='#{registration_date}' where user_id='#{user_id}'")
    int updateUser(UserEntity userEntity);
}
