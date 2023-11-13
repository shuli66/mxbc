package com.mxbc.mapper;

import com.mxbc.Entity.EmployeeEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LoginMapper {
    @Select("select * from employee where username = #{userName} and password_hash = #{passwordHash}")
    EmployeeEntity findByUsernameAndPassword(@Param("userName") String userName, @Param("passwordHash") String passwordHash);
}
