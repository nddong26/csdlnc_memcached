package com.memcached.connection;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import java.util.ResourceBundle;

public class MemcachedPool {
    public static MemCachedClient memCachedClient = new MemCachedClient();

    static ResourceBundle rb = ResourceBundle.getBundle("config");

    static {
        String[] servers
                = {
                rb.getString("MEMCACHED_URL") + ":" + rb.getString("MEMCACHED_PORT")};
        Integer[] weights = {1};
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.setWeights(weights);
        pool.setInitConn(20);
        pool.setMinConn(20);
        pool.setMaxConn(Integer.parseInt(rb.getString("MEMCACHED_MAX_CONNECTION")));
        pool.setMaxIdle(1000 * 60 * 60);
        pool.initialize();
    }





}
