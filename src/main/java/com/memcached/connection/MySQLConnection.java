package com.memcached.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class MySQLConnection {
    public static ResourceBundle rb = ResourceBundle.getBundle("config");

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(rb.getString("DB_URL"), rb.getString("username"), rb.getString("password"));
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }
}
