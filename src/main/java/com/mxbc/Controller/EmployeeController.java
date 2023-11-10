package com.mxbc.Controller;

import com.mxbc.Basedao.BaseController;
import com.mxbc.Entity.EmployeeEntity;
import com.mxbc.mapper.EmployeeMapper;
import com.mxbc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("employee")
public class EmployeeController extends BaseController {
    @Autowired(required = false)
    private EmployeeService employeeService;
    @GetMapping("/getEmployeeAll")
    public List<EmployeeEntity> getEmployeeAll(){
        return employeeService.getEmployeeAll();
    }
    @GetMapping("/getEmployeeById")
    public EmployeeEntity getEmployeeById(Integer id){
        return employeeService.getEmployeeById(id);
    }
    @PostMapping("/insertEmployee")
    public Map<String,Object> insertEmployee(@RequestBody EmployeeEntity employeeEntity){
        Timestamp date=new Timestamp(System.currentTimeMillis());
        employeeEntity.setCreatedAt(date);

        return employeeService.insertEmployee(employeeEntity) > 0 ?
               setResultOk("插入成功"):setResultError("插入失败");

    }
    @DeleteMapping("/deleteEmployee")
    public Map<String, Object> deleteEmployee(Integer id){
        return employeeService.deleteEmployee(id)> 0 ?
                setResultSuccess("删除成功") : setResultError("删除失败");
    }
    @PutMapping("/updateEmployee")
    public Map<String,Object> updateEmployee(@RequestBody EmployeeEntity employeeEntity){
        Timestamp date=new Timestamp(System.currentTimeMillis());
        employeeEntity.setCreatedAt(date);
        return employeeService.updateEmployee(employeeEntity)> 0 ?
                setResultOk("修改成功"):setResultError("修改失败");
    }
}
