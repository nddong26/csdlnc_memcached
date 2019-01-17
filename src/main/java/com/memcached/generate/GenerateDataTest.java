package com.memcached.generate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenerateDataTest {
        /*Bảng 1: Product: 10.000
                - Product_id
                - Product_name
                - Product_price
                - Product_description
                - Status
        Bảng 2: Customer: 1.000.000
                - Customer_id
                - Customer_name
                - Sex
                - Status
        Bảng 4: Orders: 20.000.000
                - Order_id
                - Customer_id
                - Buy_date
                - status
        Bảng 5: Order_detail: 80.000.000
                - Order_detail_id
                - Order_id
                - Product_id
                - Price
        */

    public static void main(String[] args) throws IOException {
        //create product: 10.000 rows
        Map<Integer, Double> mapPrice = new HashMap();
        BufferedWriter writerProduct = new BufferedWriter(new FileWriter("product.txt", false));
        for (int i = 1; i <= 10000; i++) {
            Double productPrice = Generator.generatedPrice();
            writerProduct.write(i + "\t" + "Product " + Generator.generateName() + "\t" + productPrice + "\t\\N\t1");
            writerProduct.newLine();
            mapPrice.put(i, productPrice);
        }
        writerProduct.close();
        System.out.println("done generate product");
        //create Customer: 1.000.000 rows
        BufferedWriter writerCustomer = new BufferedWriter(new FileWriter("customer.txt", false));
        for (int i = 1; i <= 1000000; i++) {
            writerCustomer.write(i + "\t" + Generator.generateName() + " " + Generator.generateName() + "\t" + Generator.generatedSex() + "\t1");
            writerCustomer.newLine();
        }
        writerCustomer.close();
        System.out.println("done generate customer");
        //create Orders: 20.000.000 rows
        BufferedWriter writerOrder = new BufferedWriter(new FileWriter("order.txt", false));
        BufferedWriter writerOrderWithDetail = new BufferedWriter(new FileWriter("order_with_detail.txt", false));
        BufferedWriter writerOrderDetail = new BufferedWriter(new FileWriter("order_detail.txt", false));
        int orderDetailId = 1;
        for (int i = 1; i <= 20000000; i++) {
            String orderDetail = "";
            for (int j = 0; j < 4; j++) {
                int productId = Generator.getRandomNumberBellow(10000);
                Double productPrice = mapPrice.get(productId);
                writerOrderDetail.write(orderDetailId + "\t" + i + "\t" + productId + "\t" + productPrice);
                orderDetail += orderDetailId + "_";

                orderDetailId++;
                writerOrderDetail.newLine();

            }
            writerOrder.write(i + "\t" + Generator.getRandomNumberBellow(1000000) + "\t" + Generator.createRandomDate() + "\t1");
            writerOrderWithDetail.write(i + "\t" + Generator.getRandomNumberBellow(1000000) + "\t" + Generator.createRandomDate() + "\t1\t" + orderDetail);
            writerOrderWithDetail.newLine();
            writerOrder.newLine();
            if (i % 100000 == 0) {
                writerOrder.flush();
                writerOrderDetail.flush();
            }
        }
        writerOrder.close();
        writerOrderDetail.close();
        System.out.println("done generate order and order detail");
        //create Orders detail: 80.000.000 rows
    }
}
