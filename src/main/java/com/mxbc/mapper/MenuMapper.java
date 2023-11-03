package com.mxbc.mapper;

import com.mxbc.entity.MenuEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MenuMapper {

    @Select("select * from menu where menu_id = #{menu_id}")
    int getMenuId(Integer menu_id);

    @Select("select * from menu")
    List<MenuEntity> getMenu();

    @Insert("INSERT INTO `mxbc`.`menu` (`menu_id`, `item_name`, `price`, `description`, `available_quantity`, `creation_date`, `photo_url`) VALUES (null, #{item_name}, #{price}, #{description}, #{available_quantity}, #{creation_date}, #{photo_url})")
    int insertMenu(MenuEntity menuEntity);

    @Delete("delete from menu where menu_id = #{menu_id}")
    int deleteMenu(Integer menu_id);

    @Update("update menu set item_name = #{item_name}, price = #{price}, description = #{description}, available_quantity = #{available_quantity}, photo_url = #{photo_url} where menu_id = #{menu_id}")
    int updateMenu(MenuEntity menuEntity);








}
