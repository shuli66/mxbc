package com.mxbc.Controller;
import cn.hutool.core.util.StrUtil;
import com.mxbc.Config.AuthAccess;
import com.mxbc.Config.Result;
import com.mxbc.Entity.EmployeeEntity;
import com.mxbc.service.EmployeeService;
import com.mxbc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Map;


@RestController
public class LoginController extends Result {

    @Autowired
    private LoginService loginService;

    @Autowired
    private EmployeeService employeeService;
    @AuthAccess
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody EmployeeEntity user) {
        if (StrUtil.isBlank(user.getUserName()) ||StrUtil.isBlank(user.getPasswordHash())){
                return setResultEorMsg("数据输入不合法");
        }
        EmployeeEntity user2 = loginService.login(user);
        return setResultOk(user2);
    }
    @AuthAccess
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody EmployeeEntity user) {
        if (StrUtil.isBlank(user.getUserName()) ||StrUtil.isBlank(user.getPasswordHash())){
            return setResultEorMsg("数据输入不合法");
        }
        if (user.getUserName().length()>10 ||user.getPasswordHash().length()>15){
            return setResultEorMsg("数据输入不合法");
        }
        EmployeeEntity user2 = loginService.register(user);
        Timestamp date=new Timestamp(System.currentTimeMillis());
        user2.setCreatedAt(date);
        return setResultOk(user2);
    }

    /**
     *  Controller重置密码
     * @return
     */
    @AuthAccess
    @PutMapping("/password")
    public Map<String, Object> password(@RequestBody EmployeeEntity user) {
        if (StrUtil.isBlank(user.getUserName()) || StrUtil.isBlank(user.getPhoneNumber())) {
            return Result.setResultEorMsg("数据输入不合法");
        }
        employeeService.resetPassword(user);
        return Result.setResultSuccess("ok");
    }




}
