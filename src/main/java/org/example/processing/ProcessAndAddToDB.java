package org.example.processing;

import org.example.mapper.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProcessAndAddToDB  {
    ArrayList<ObjectMapper> myArrayList=null;
    Connection conn=null;
    public ProcessAndAddToDB(ArrayList<ObjectMapper> paramObj,Connection conn)
    {
        this.myArrayList=paramObj;
        this.conn=conn;
    }

    public void run() {
        try {
            String sql = "INSERT INTO product.product_entity (id,Image_url, product_id, product_name, product_price, product_type)\n" +
                    "VALUES (?,?, ?, ?, ?, ?);";
        PreparedStatement prestmt = conn.prepareStatement(sql);
        this.myArrayList.forEach((res) -> {
                try {
                    prestmt.setInt(1,res.getId());
                    prestmt.setString(2, res.getImageUrl());
                    prestmt.setString(3, res.getProductId());
                    prestmt.setString(4, res.getProductName());
                    prestmt.setFloat(5, res.getProductPrice());
                    prestmt.setString(6, res.getProductType());
                    prestmt.addBatch();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            });

            prestmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
