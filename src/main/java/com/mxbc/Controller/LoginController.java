package com.mxbc.Controller;
import com.mxbc.Basedao.BaseController;
import com.mxbc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mxbc.Entity.EmployeeEntity;

import java.util.Map;


@RestController
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody EmployeeEntity loginRequest) {
        // 从数据库中查询用户
        EmployeeEntity employee = loginService.findByUsernameAndPassword(
                loginRequest.getUserName(), loginRequest.getPasswordHash());

        // 验证用户是否存在
        if (employee != null) {
            // 登录成功
            return setResultOk(employee);
        } else {
            // 登录失败
            return setResultError("用户名或密码错误");
        }
    }
}
