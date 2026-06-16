package com.mftech.monolith_ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private String name;
    private String description;
    private BigDecimal price;
    private String pack;
    private Integer stock;
    private String category;
    private String image;
    private Boolean isActive;
}
