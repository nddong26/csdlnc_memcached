package com.memcached.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Orders implements Serializable {
    private Long orderId;
    private Long customerId;
    //    private Date buyDate;
//    private Long status;
    private String orderDetailIds;

    public Orders() {
    }

    public Orders(String[] line) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        orderId = Long.parseLong(line[0]);
        customerId = Long.parseLong(line[1]);
//        buyDate = sdf.parse(line[2]);
//        status = Long.parseLong(line[3]);
        orderDetailIds = line[4];
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getOrderDetailIds() {
        return orderDetailIds;
    }

    public void setOrderDetailIds(String orderDetailIds) {
        this.orderDetailIds = orderDetailIds;
    }
//    public Date getBuyDate() {
//        return buyDate;
//    }
//
//    public void setBuyDate(Date buyDate) {
//        this.buyDate = buyDate;
//    }
//
//    public Long getStatus() {
//        return status;
//    }
//
//    public void setStatus(Long status) {
//        this.status = status;
//    }
}
