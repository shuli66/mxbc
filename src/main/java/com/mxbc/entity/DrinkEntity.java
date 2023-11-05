package com.mxbc.entity;

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
    private Integer id; // 饮品ID

    private String name; // 饮品名称

    private Integer category_id; // 分类ID

    private BigDecimal price; // 价格

    private String description; // 描述

    @Column(name = "image_url")
    private String image; // 照片链接

    @Column(name = "is_available", columnDefinition = "TINYINT(1) default 1")
    private Boolean isAvailable; // 是否可用

    @Column(name = "promotional_price")
    private BigDecimal promotionalPrice; // 促销价格
}

