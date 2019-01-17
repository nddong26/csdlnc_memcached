package com.memcached.domain;

import java.io.Serializable;

public class Customer implements Serializable {
    private Long customerId;
    private String customerName;

    //    private Long sex;
//    private Long status;
//
    public Customer() {
    }

    public Customer(String[] line) {
        customerId = Long.parseLong(line[0]);
        customerName = line[1];
//        sex = Long.parseLong(line[2]);
//        status = Long.parseLong(line[3]);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

//    public Long getSex() {
//        return sex;
//    }
//
//    public void setSex(Long sex) {
//        this.sex = sex;
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
