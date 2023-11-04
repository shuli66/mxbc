package com.mxbc.controller;

import com.mxbc.basedao.BaseController;
import com.mxbc.entity.UserEntity;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mxbc.service.UserService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired(required = false)
    private UserService userService;
    @GetMapping("/getUserById")
    public UserEntity getUserById(Integer id){
        return userService.getUserById(id);
    }
    @GetMapping("/getUserAll")
    public List<UserEntity> getUserAll(){
        return userService.getUserAll();
    }

    @PostMapping("/insertUser")
    public Map<String,Object> insertUser(@RequestBody UserEntity userEntity){
        return userService.insertUser(userEntity)>  0  ?
                setResultOk("插入成功") : setResultError("插入失败");
    }
    @DeleteMapping("/deleteUser")
    public Map<String,Object> deleteUser( Integer id){
        return userService.deleteUser(id)> 0 ?
                setResultSuccess("删除成功") : setResultError("删除失败");
    }
    @PutMapping("/updateUser")
    public Map<String,Object> updateUser(@RequestBody UserEntity userEntity){
        return userService.updateUser(userEntity)> 0 ?
                setResultOk("更新成功") : setResultError("更新失败");
    }

}
