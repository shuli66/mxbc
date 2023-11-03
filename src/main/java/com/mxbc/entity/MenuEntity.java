package com.mxbc.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MenuEntity {

    private Integer menu_id; // 菜单id

    private String item_name; // 菜单项名称

    private BigDecimal price; // 价格

    private String description; // 描述

    private int available_quantity; // 可用库存


    private Timestamp creation_date;

    private String photoUrl; // 照片链接

    public Integer getId() {
        return menu_id;
    }

    public void setId(Integer id) {
        this.menu_id = menu_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Timestamp getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Timestamp creation_date) {
        this.creation_date = creation_date;
    }
}

