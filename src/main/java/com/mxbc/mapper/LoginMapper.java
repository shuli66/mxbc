package com.mxbc.mapper;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LoginMapper {
    @Select("SELECT * FROM user WHERE (password_hash = #{passwordHash} phone_number = #{phoneNumber})")
    User loginUser(@Param("passwordHash") String passwordHash,@Param("phoneNumber") String phoneNumber);
}
