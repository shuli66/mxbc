package com.mxbc.Controller;

import com.mxbc.Config.Result;
import com.mxbc.Entity.EmployeeEntity;
import com.mxbc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;

@RestController
@RequestMapping("employee")
public class EmployeeController extends Result {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/getEmployeeAll")
    public Map<String, Object> getEmployeeAll(){
        return Result.setResultOk(employeeService.getEmployeeAll());
    }
    @GetMapping("/getEmployeeById/{id}")
    public Map<String, Object> getEmployeeById(@PathVariable Integer id){
        return Result.setResultOk(employeeService.getEmployeeById(id));
    }
    @PostMapping("/insertEmployee")
    public Map<String,Object> insertEmployee(@RequestBody EmployeeEntity employeeEntity){


        return employeeService.insertEmployee(employeeEntity) > 0 ?
               setResultOk("插入成功"):setResultError("插入失败");

    }
    @DeleteMapping("/deleteEmployee/{id}")
    public Map<String, Object> deleteEmployee(@PathVariable Integer id){
        return employeeService.deleteEmployee(id)> 0 ?
                setResultSuccess("删除成功") : setResultError("删除失败");
    }
    @PutMapping("/updateEmployee")
    public Map<String,Object> updateEmployee(@RequestBody EmployeeEntity employeeEntity){

        return employeeService.updateEmployee(employeeEntity)> 0 ?
                setResultOk("更新成功") : setResultError("更新失败");
    }

    @GetMapping("/searchEmployee")
    public Map<String, Object> searchEmployee(@RequestParam(value = "searchAdminName", required = false) String searchName) {
        return Result.setResultOk(employeeService.searchEmployee(searchName));
    }

}
