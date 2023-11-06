package com.mxbc.service;

import com.mxbc.Entity.DrinkEntity;
import com.mxbc.mapper.DrinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkService {

    @Autowired(required = false)
    private DrinkMapper drinkMapper;

    public DrinkEntity getDrinkById(Integer id){
        return drinkMapper.getDrinkById(id);
    }

    public List<DrinkEntity> getDrinkAll(){
        return drinkMapper.getDrinkAll();
    }

    public int updateDrink(DrinkEntity drinkEntity){
       return drinkMapper.updateDrink(drinkEntity);
    }

    public int deleteDrink(Integer id){
        return drinkMapper.deleteDrink(id);
    }

    public int insertDrink(DrinkEntity drinkEntity){
        return drinkMapper.insertDrink(drinkEntity);
    }
}
