package com.mxbc.mapper;

import com.mxbc.entity.MenuEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MenuMapper {

    @Select("SELECT * FROM menu WHERE menu_id = #{menuId}")
    MenuEntity getMenuById(Integer menuId);

    @Select("SELECT * FROM menu")
    List<MenuEntity> getAllMenus();

    @Insert("INSERT INTO menu (menu_id, item_name, price, description, available_quantity, creation_date, photo_url) " +
            "VALUES (null, #{itemName}, #{price}, #{description}, #{availableQuantity}, #{creationDate}, #{photoUrl})")
    int insertMenu(MenuEntity menuEntity);

    @Delete("DELETE FROM menu WHERE menu_id = #{menuId}")
    int deleteMenu(Integer menuId);

    @Update("UPDATE menu SET item_name = #{itemName}, price = #{price}, " +
            "description = #{description}, available_quantity = #{availableQuantity}, creation_date = #{creationDate}, " +
            "photo_url = #{photoUrl} WHERE menu_id = #{menuId}")
    int updateMenu(MenuEntity menuEntity);
}
