package com.mxbc.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "drink")
public class DrinkEntity {
    @Id
    @Column(name = "drink_id")
    private Integer drinkId; // 饮品ID

    private String name; // 饮品名称

    @Column(name = "category_id")
    private Integer categoryId; // 分类ID

    private BigDecimal price; // 价格

    private String description; // 描述

    @Column(name = "image_url")
    private String imageUrl; // 照片链接

    @Column(name = "is_available", columnDefinition = "TINYINT(1) default 1")
    private Boolean isAvailable; // 是否可用

    @Column(name = "promotional_price")
    private BigDecimal promotionalPrice; // 促销价格

    public Integer getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(Integer drinkId) {
        this.drinkId = drinkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public BigDecimal getPromotionalPrice() {
        return promotionalPrice;
    }

    public void setPromotionalPrice(BigDecimal promotionalPrice) {
        this.promotionalPrice = promotionalPrice;
    }
}

