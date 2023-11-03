package com.mxbc.service;

import com.mxbc.entity.MenuEntity;
import com.mxbc.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService{

    @Autowired(required = false)
    private MenuMapper menuMapper;

    public int getMenuId(Integer menu_id){
        return menuMapper.getMenuId(menu_id);
    }

    public List<MenuEntity> getMenu(){
        return menuMapper.getMenu();
    }

    public int updateMenu(MenuEntity menuEntity){
       return menuMapper.updateMenu(menuEntity);
    }

    public int deleteMenu(Integer menu_id){
        return menuMapper.deleteMenu(menu_id);
    }

    public int insertMenu(MenuEntity menuEntity){
        return menuMapper.insertMenu(menuEntity);
    }
}