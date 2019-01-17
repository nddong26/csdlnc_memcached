package com.memcached.domain;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private Long orderDetailId;
    private Long orderId;
    private Long productId;
    private Double productPrice;

    public OrderDetail(String[] line) {
        orderDetailId = Long.parseLong(line[0]);
        orderId = Long.parseLong(line[1]);
        productId = Long.parseLong(line[2]);
        productPrice = Double.parseDouble(line[3]);
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
