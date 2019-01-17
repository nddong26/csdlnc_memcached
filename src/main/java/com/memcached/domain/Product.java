package com.memcached.domain;

import java.io.Serializable;

public class Product implements Serializable {
    private Long productId;
    private String productName;
    private Double productPrice;
    private String productDescription;
    private Long status;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Product() {
    }

    public Product(String[] line) {
        productId = Long.parseLong(line[0]);
        productName = line[1];
        productPrice = Double.parseDouble(line[2]);
        productDescription = line[3];
        status = Long.parseLong(line[4]);
    }
}
