package com.mxbc.service;

import com.mxbc.Entity.EmployeeEntity;
import com.mxbc.Entity.UserEntity;
import com.mxbc.expection.ServiceException;
import com.mxbc.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    public List<EmployeeEntity> getEmployeeAll() {
        return employeeMapper.getEmployeeAll();

    }

    public EmployeeEntity getEmployeeById(Integer id) {
        return employeeMapper.getEmployeeById(id);
    }

    public int deleteEmployee(Integer id) {
        return employeeMapper.deleteEmployee(id);
    }

    public int insertEmployee(EmployeeEntity employeeEntity) {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        employeeEntity.setCreatedAt(date);
        return employeeMapper.insertEmployee(employeeEntity);
    }

    public int updateEmployee(EmployeeEntity employeeEntity) {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        employeeEntity.setCreatedAt(date);
        return employeeMapper.updateEmployee(employeeEntity);
    }


    //  Service代码：
    public void resetPassword(EmployeeEntity user) {
        EmployeeEntity dbUser = employeeMapper.selectByName(user.getUserName());
        if (dbUser == null) {
            // 抛出一个自定义的异常
            throw new ServiceException("用户不存在");
        }
        if (!user.getPhoneNumber().equals(dbUser.getPhoneNumber())) {
            throw new ServiceException("验证错误");
        }
        dbUser.setPasswordHash("123");   // 重置密码
        employeeMapper.updateEmployee(dbUser);
    }

    public List<EmployeeEntity> searchEmployee(String name) {
        System.out.println(employeeMapper.searchEmployee(name));
        return employeeMapper.searchEmployee(name);
    }
}
