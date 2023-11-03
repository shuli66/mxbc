package com.mxbc.controller;

import com.mxbc.basedao.BaseController;
import com.mxbc.entity.MenuEntity;
import com.mxbc.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/getMenuId")
    public Map<String, Object> getMenuId(Integer id){

        return menuService.getMenuId(id)>0 ? setResultSuccess("获取成功") : setResultError("获取失败");
    }

    @GetMapping("/getMenu")
    public Map<String, Object> getMenu(){
        List<MenuEntity> menuEntities = menuService.getMenu();
        return  setResultOk(menuEntities) ;
    }



}
