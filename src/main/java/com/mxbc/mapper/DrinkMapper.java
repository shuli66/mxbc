package com.mxbc.mapper;

import com.mxbc.entity.DrinkEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DrinkMapper {

    @Select("SELECT * FROM drink WHERE drink_id = #{id}")
    DrinkEntity getDrinkById(Integer id);

    @Select("SELECT * FROM drink")
    List<DrinkEntity> getDrinkAll();

    @Insert("INSERT INTO drink (drink_id, name, category_id, price, description, image_url, is_available, promotional_price) " +
            "VALUES (null, #{name}, #{category_id}, #{price}, #{description}, #{image}, #{isAvailable}, #{promotionalPrice})")
    int insertDrink(DrinkEntity drinkEntity);

    @Delete("DELETE FROM drink WHERE drink_id = #{id}")
    int deleteDrink(Integer id);

    @Update("UPDATE drink SET name = #{name}, category_id = #{category_id}, " +
            "price = #{price}, description = #{description}, image_url = #{image}, " +
            "is_available = #{isAvailable}, promotional_price = #{promotionalPrice} WHERE drink_id = #{id}")
    int updateDrink(DrinkEntity drinkEntity);
}
