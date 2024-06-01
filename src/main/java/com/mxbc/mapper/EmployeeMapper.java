package com.mxbc.mapper;

import com.mxbc.Entity.EmployeeEntity;
import com.mxbc.Entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface EmployeeMapper {
    @Select("select * from employee")
    List<EmployeeEntity> getEmployeeAll();

    @Select("select * from employee where employee_id = #{employeeId}")
    EmployeeEntity getEmployeeById(Integer id);

    @Select("select * from employee where employee.username = #{userName}")
    EmployeeEntity selectByName(String userName);


    @Insert("insert into employee (employee_id,username,phone_number,password_hash,email,employee_type,created_at) " +
            "values(null,#{userName},#{phoneNumber},#{passwordHash},#{email},#{employeeType},#{createdAt}) ")
    int insertEmployee(EmployeeEntity employeeEntity);


    @Delete("delete from employee where employee_id = #{employeeId}")
    int deleteEmployee(Integer id);

    @Update({
            "<script>",
            "UPDATE `milkdb`.`employee`",
            "<set>",
            "<if test='userName != null'>`username`=#{userName},</if>",
            "<if test='passwordHash != null'>`password_hash`=#{passwordHash},</if>",
            "<if test='phoneNumber != null'>`phone_number`=#{phoneNumber},</if>",
            "<if test='email != null'>`email`=#{email},</if>",
            "<if test='employeeType != null'>`employee_type`=#{employeeType},</if>",
            "<if test='createdAt != null'>`created_at`=#{createdAt},</if>",
            "<if test='avatar != null'>`avatar`=#{avatar},</if>",
            "</set>",
            "WHERE `employee_id`=#{employeeId}",
            "</script>"
    })
    int updateEmployee(EmployeeEntity employeeEntity);


    @Select("SELECT * FROM employee WHERE username LIKE CONCAT('%', #{name}, '%')")
    List<EmployeeEntity> searchEmployee(@Param("name") String name);

}
