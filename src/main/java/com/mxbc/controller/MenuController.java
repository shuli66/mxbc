package com.mxbc.controller;

import com.mxbc.basedao.BaseController;
import com.mxbc.entity.MenuEntity;
import com.mxbc.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    /**
     * 根据id查询菜单*
     * @param id
     * @return
     */


    @GetMapping("/getMenuById")
    public Map<String, Object> getMenuId(Integer id){
        MenuEntity menu = menuService.getMenuId(id);
        return setResultOk(menu);
    }


    /**
     * 查询所有菜单*
     * @return
     */

    @GetMapping("/getMenuAll")
    public Map<String, Object> getMenu(){
        List<MenuEntity> menuEntities = menuService.getMenu();
        return  setResultOk(menuEntities);
    }

    /**
     * 删除
     * @param id
     * @return
     */

    @DeleteMapping("/deleteMenu")
    public Map<String,Object> deleteMenu(Integer id){
        return menuService.deleteMenu(id)>0 ? setResultSuccess("删除成功！") : setResultError("删除失败！");
    }

    /**
     * 修改
     * @param menuEntity
     * @return
     */
    @PutMapping("/updateMenu")
    public Map<String, Object> updateMenu(@RequestBody MenuEntity menuEntity) {
        // 获取当前时间
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        // 设置更新时间
        menuEntity.setCreationDate(currentTimestamp);

        return menuService.updateMenu(menuEntity) > 0 ? setResultOk("更新成功！") : setResultError("更新失败！");
    }


    /**
     * 添加*
     * @param menuEntity
     * @return
     */
    @PostMapping("/insertMenu")
    public Map<String,Object> insertMenu(@RequestBody MenuEntity menuEntity){
        menuEntity.setCreationDate(new Timestamp(System.currentTimeMillis()));
        return menuService.insertMenu(menuEntity)>0 ? setResultOk("添加成功！") : setResultError("添加失败！");
    }

}
