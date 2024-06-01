package com.mxbc.Controller;

import com.mxbc.Config.Result;
import com.mxbc.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mxbc.service.UserService;
import java.sql.Timestamp;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController extends Result {
    @Autowired
    private UserService userService;

    @GetMapping("/getUserById")
    public UserEntity getUserById(Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getUserAll")
    public Map<String, Object> getUserAll() {
        Object data = userService.getUserAll();
        return setResult("200", "查找成功", data);

    }

    @PostMapping("/insertUser")
    public Map<String, Object> insertUser(@RequestBody UserEntity userEntity) {
        // 获取当前时间
        Timestamp date = new Timestamp(System.currentTimeMillis());
        // 设置更新时间
        userEntity.setCreatedAt(date);
        return userService.insertUser(userEntity) > 0 ?
                setResultOk("插入成功") : setResultError("插入失败");
    }

    @DeleteMapping("/deleteUser/{id}")
    public Map<String, Object> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id) > 0 ?
                setResultSuccess("删除成功") : setResultError("删除失败");
    }

    @PutMapping("/updateUser")
    public Map<String, Object> updateUser(@RequestBody UserEntity userEntity) {

        return userService.updateUser(userEntity) > 0 ?
                setResultOk("更新成功") : setResultError("更新失败");
    }

    //查询
    @GetMapping("/searchUser")
    public Map<String, Object> searchUser(@RequestParam(value = "searchName", required = false) String searchName) {

        return Result.setResultOk(userService.searchUser(searchName));
    }



}
