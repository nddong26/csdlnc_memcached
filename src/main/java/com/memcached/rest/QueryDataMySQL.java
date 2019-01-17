package com.memcached.rest;

import com.memcached.connection.MySQLConnection;
import com.memcached.generate.Generator;
import com.memcached.load.ImportFromFile2Memcached;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Path("/json/query")
public class QueryDataMySQL {

    ResourceBundle rb = ResourceBundle.getBundle("config");



//    @POST
//    @Path("/importMemcached")
//    @Consumes("application/json")
//    public Response createProductInJSON(Product product) {
//
//        String result = "Product created : " + product;
//        return Response.status(201).entity(result).build();
//
//    }

}