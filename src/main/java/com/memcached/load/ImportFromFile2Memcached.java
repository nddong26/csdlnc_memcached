package com.memcached.load;

import com.memcached.connection.MemcachedPool;
import com.memcached.domain.Customer;
import com.memcached.domain.OrderDetail;
import com.memcached.domain.Orders;
import com.memcached.domain.Product;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class ImportFromFile2Memcached implements Runnable {

    public static final Queue<Object> queue = new LinkedBlockingDeque<Object>();


    @Override
    public void run() {
        while (!queue.isEmpty()) {
            Object obj = queue.poll();
            if (obj instanceof Product) {
                MemcachedPool.memCachedClient.set("PRODUCT_" + ((Product) obj).getProductId(), obj);
            } else if (obj instanceof Customer) {
                MemcachedPool.memCachedClient.set("CUSTOMER_" + ((Customer) obj).getCustomerId(), obj);
            } else if (obj instanceof Orders) {
                MemcachedPool.memCachedClient.set("ORDER_" + ((Orders) obj).getOrderId(), obj);
            } else if (obj instanceof OrderDetail) {
                MemcachedPool.memCachedClient.set("ORDERDETAIL_" + ((OrderDetail) obj).getOrderDetailId(), obj);
            }
        }
    }
}
