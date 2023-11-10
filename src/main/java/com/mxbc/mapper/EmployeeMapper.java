package com.mxbc.mapper;

import com.mxbc.Entity.EmployeeEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EmployeeMapper {
    @Select("select * from employee")
    List<EmployeeEntity> getEmployeeAll();

    @Select("select * from employee where employee_id = #{employeeId}")
    EmployeeEntity getEmployeeById(Integer id);

    @Insert("insert into employee (employee_id,username,phone_number,password_hash,email,employee_type,created_at) " +
            "values(null,#{userName},#{phoneNumber},#{passwordHash},#{email},#{employeeType},#{createdAt})")
    int insertEmployee(EmployeeEntity employeeEntity);


    @Delete("delete from employee where employee_id = #{employeeId}")
    int deleteEmployee(Integer id);

    @Update("UPDATE `milkdb`.`employee` SET `username`=#{userName}, `password_hash`=#{passwordHash}, `phone_number`=#{phoneNumber}, `email`=#{email}, `employee_type`=#{employeeType}, `created_at`=#{createdAt}" +
            " WHERE (`employee_id`=#{employeeId});")
    int updateEmployee(EmployeeEntity employeeEntity);
}
