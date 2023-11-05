package com.mxbc.controller;

import com.mxbc.basedao.BaseController;
import com.mxbc.entity.DrinkEntity;
import com.mxbc.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drink")
public class MenuController extends BaseController {

    @Autowired
    private DrinkService drinkService;

    /**
     * 根据id查询菜单*
     * @param id
     * @return
     */


    @GetMapping("/getDrinkById")
    public Map<String, Object> getDrinkById(Integer id){
        DrinkEntity menu = drinkService.getDrinkById(id);
        return setResultOk(menu);
    }


    /**
     * 查询所有菜单*
     * @return
     */

    @GetMapping("/getDrinkAll")
    public Map<String, Object> getMenu(){
        List<DrinkEntity> menuEntities = drinkService.getDrinkAll();
        return  setResultOk(menuEntities);
    }

    /**
     * 删除
     * @param id
     * @return
     */

    @DeleteMapping("/deleteDrink")
    public Map<String,Object> deleteDrink(Integer id){
        return drinkService.deleteDrink(id)>0 ? setResultSuccess("删除成功！") : setResultError("删除失败！");
    }

    /**
     * 修改
     * @param drinkEntity
     * @return
     */
    @PutMapping("/updateDrink")
    public Map<String, Object> updateDrink(@RequestBody DrinkEntity drinkEntity) {

        return drinkService.updateDrink(drinkEntity) > 0 ? setResultOk("更新成功！") : setResultError("更新失败！");
    }


    /**
     * 添加*
     * @param drinkEntity
     * @return
     */
    @PostMapping("/insertMenu")
    public Map<String,Object> insertMenu(@RequestBody DrinkEntity drinkEntity){

        return drinkService.insertDrink(drinkEntity)>0 ? setResultOk("添加成功！") : setResultError("添加失败！");
    }

}
