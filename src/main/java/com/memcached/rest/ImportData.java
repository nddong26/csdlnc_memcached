package com.memcached.rest;

import com.memcached.connection.MemcachedPool;
import com.memcached.connection.MySQLConnection;
import com.memcached.domain.Customer;
import com.memcached.domain.OrderDetail;
import com.memcached.domain.Orders;
import com.memcached.domain.Product;
import com.memcached.generate.Generator;
import com.memcached.load.ImportFromFile2Memcached;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

@Path("/json")
public class ImportData {

    ResourceBundle rb = ResourceBundle.getBundle("config");

    @GET
    @Path("/importMemcached")
    @Produces("application/json")
    public Response importMemcached() throws FileNotFoundException, InterruptedException, ParseException {
        long beginTime = System.currentTimeMillis();
        System.out.println("import product");
        long fromTime = System.currentTimeMillis();
        Scanner scanner = new Scanner(new File(rb.getString("DATA_FILE_FOLDER") + "/product.txt"));
        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split("\t");
            ImportFromFile2Memcached.queue.add(new Product(line));
        }
        createThreadImport();

        System.out.println("process_import_product_time: " + (System.currentTimeMillis() - fromTime));

        System.out.println("import customer");
        fromTime = System.currentTimeMillis();
        Scanner customer = new Scanner(new File(rb.getString("DATA_FILE_FOLDER") + "/customer.txt"));
        while (customer.hasNext()) {
            String[] line = customer.nextLine().split("\t");
            ImportFromFile2Memcached.queue.add(new Customer(line));
        }
        createThreadImport();

        System.out.println("process_import_customer_time: " + (System.currentTimeMillis() - fromTime));

        System.out.println("import order");
        fromTime = System.currentTimeMillis();
        Scanner orders = new Scanner(new File(rb.getString("DATA_FILE_FOLDER") + "/order_with_detail.txt"));
        while (orders.hasNext()) {
            String[] line = orders.nextLine().split("\t");
            ImportFromFile2Memcached.queue.add(new Orders(line));
            if (ImportFromFile2Memcached.queue.size() > 100000) {
                createThreadImport();
            }
        }
        createThreadImport();

        System.out.println("process_import_order_time: " + (System.currentTimeMillis() - fromTime));


        System.out.println("import order_detail");
        fromTime = System.currentTimeMillis();
        Scanner orderDetail = new Scanner(new File(rb.getString("DATA_FILE_FOLDER") + "/order_detail.txt"));
        while (orderDetail.hasNext()) {
            String[] line = orderDetail.nextLine().split("\t");
            ImportFromFile2Memcached.queue.add(new OrderDetail(line));
            if (ImportFromFile2Memcached.queue.size() > 100000) {
                createThreadImport();
            }
        }
        createThreadImport();

        System.out.println("process_import_order_detail_time: " + (System.currentTimeMillis() - fromTime));


        long endTime = System.currentTimeMillis();
        System.out.println("process_import_memcached_time: " + (endTime - beginTime));
        BaseResponse baseResponse = new BaseResponse(endTime - beginTime, endTime - beginTime);
        return Response.status(200).entity(baseResponse).build();
    }


    public void createThreadImport() throws InterruptedException {
        System.out.println("tao thread import: " + ImportFromFile2Memcached.queue.size());
        List<Thread> listThread = new ArrayList();
        for (int i = 0; i < Integer.parseInt(rb.getString("MEMCACHED_MAX_CONNECTION")); i++) {
            Thread thread = new Thread(new ImportFromFile2Memcached());
            thread.start();
            listThread.add(thread);
        }
        for (Thread thread : listThread) {
            thread.join();
        }
    }


    @GET
    @Path("/mysql/getCustomerById")
    @Produces("application/json")
    public Response getCustomerById() throws FileNotFoundException, InterruptedException, ParseException, SQLException {

        Connection connection = MySQLConnection.getConnection();
        int numberOfQuery = 10;
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfQuery; i++) {
            int customerId = Generator.getRandomNumberBellow(1000000);
            getCustomerbyId(customerId, connection);
        }
        long endTime = System.currentTimeMillis();
        connection.close();
        BaseResponse baseResponse = new BaseResponse(endTime - beginTime, (endTime - beginTime) / numberOfQuery);


        return Response.status(200).entity(baseResponse).build();
    }

    @GET
    @Path("/memcached/getCustomerById")
    @Produces("application/json")
    public Response getCustomerByIdMemcached() throws FileNotFoundException, InterruptedException, ParseException, SQLException {

        int numberOfQuery = 10;
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfQuery; i++) {
            int customerId = Generator.getRandomNumberBellow(1000000);
            Customer customer = (Customer) MemcachedPool.memCachedClient.get("CUSTOMER_" + customerId);
            System.out.println("memcached get customer by id: " + customer.getCustomerId());
        }
        long endTime = System.currentTimeMillis();
        BaseResponse baseResponse = new BaseResponse(endTime - beginTime, (endTime - beginTime) / numberOfQuery);


        return Response.status(200).entity(baseResponse).build();
    }


    @GET
    @Path("/memcached/getOrderPrice")
    @Produces("application/json")
    public Response getOrderTotalPriceMemcached() throws FileNotFoundException, InterruptedException, ParseException, SQLException {

        int numberOfQuery = 10;
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfQuery; i++) {
            int orderId = Generator.getRandomNumberBellow(20000000);

            Orders customer = (Orders) MemcachedPool.memCachedClient.get("ORDER_" + orderId);
            String orderDetailIds = customer.getOrderDetailIds();
            String[] orderDetailList = orderDetailIds.split("_");
            Double price = 0D;
            for (String orderDetailId : orderDetailList) {
                OrderDetail orderDetail = (OrderDetail) MemcachedPool.memCachedClient.get("ORDERDETAIL_" + orderDetailId);
                price += orderDetail.getProductPrice();
            }
            System.out.println(orderId + ":" + price);

        }
        long endTime = System.currentTimeMillis();
        BaseResponse baseResponse = new BaseResponse(endTime - beginTime, (endTime - beginTime) / numberOfQuery);


        return Response.status(200).entity(baseResponse).build();
    }


    @GET
    @Path("/mysql/getOrderPrice")
    @Produces("application/json")
    public Response getOrderTotalPriceMySQL() throws FileNotFoundException, InterruptedException, ParseException, SQLException {

        Connection connection = MySQLConnection.getConnection();
        int numberOfQuery = 10;
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfQuery; i++) {
            int orderId = Generator.getRandomNumberBellow(20000000);
            getPriceByOrderId(orderId, connection);
        }
        long endTime = System.currentTimeMillis();
        connection.close();
        BaseResponse baseResponse = new BaseResponse(endTime - beginTime, (endTime - beginTime) / numberOfQuery);


        return Response.status(200).entity(baseResponse).build();
    }

    private void getPriceByOrderId(int orderId, Connection connection) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select sum(product_price) \n" +
                    "from test_db.orders a, test_db.order_detail b  \n" +
                    "where a.order_id = b.order_id \n" +
                    "and a.order_id = ? ";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(orderId + ":" + rs.getDouble(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }


    private void getCustomerbyId(int customerId, Connection connection) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from test_db.customer where customer_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("done customer:" + rs.getLong("customer_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }


    @GET
    @Path("/memcached/getOrderDetailById")
    @Produces("application/json")
    public Response getOrderDetailByIdMemcached() throws FileNotFoundException, InterruptedException, ParseException, SQLException {

        int numberOfQuery = 10;
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfQuery; i++) {
            int orderDetailId = Generator.getRandomNumberBellow(80000000);
            OrderDetail orderDetail = (OrderDetail) MemcachedPool.memCachedClient.get("ORDERDETAIL_" + orderDetailId);
            System.out.println("memcached get order_detail by id: " + orderDetail.getOrderDetailId());
        }
        long endTime = System.currentTimeMillis();
        BaseResponse baseResponse = new BaseResponse(endTime - beginTime, (endTime - beginTime) / numberOfQuery);


        return Response.status(200).entity(baseResponse).build();
    }

    @GET
    @Path("/mysql/getOrderDetailById")
    @Produces("application/json")
    public Response getOrderDetailById() throws FileNotFoundException, InterruptedException, ParseException, SQLException {

        Connection connection = MySQLConnection.getConnection();
        int numberOfQuery = 10;
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfQuery; i++) {
            int orderDetailId = Generator.getRandomNumberBellow(80000000);
            getOrderDetail(orderDetailId, connection);
        }
        long endTime = System.currentTimeMillis();
        connection.close();
        BaseResponse baseResponse = new BaseResponse(endTime - beginTime, (endTime - beginTime) / numberOfQuery);


        return Response.status(200).entity(baseResponse).build();
    }

    private void getOrderDetail(int orderDetailId, Connection connection) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from test_db.order_detail where order_detail_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderDetailId);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("done order_detail:" + rs.getLong("order_detail_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }


}