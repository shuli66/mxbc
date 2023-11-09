package com.mxbc.service;

import com.mxbc.Entity.EmployeeEntity;
import com.mxbc.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired(required = false)
    private EmployeeMapper employeeMapper;

    public List<EmployeeEntity> getEmployeeAll(){
        return employeeMapper.getEmployeeAll();

    }
    public EmployeeEntity getEmployeeById(Integer id){
        return employeeMapper.getEmployeeById(id);
    }
    public int deleteEmployee(Integer id){
        return employeeMapper.deleteEmployee(id);
    }
    public int  insertEmployee(EmployeeEntity employeeEntity){
        return  employeeMapper.insertEmployee(employeeEntity);
    }
    public int updateEmployee(EmployeeEntity employeeEntity){
        return employeeMapper.updateEmployee(employeeEntity);
    }

}
