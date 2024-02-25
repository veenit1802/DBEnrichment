package org.example.processing;

import org.example.mapper.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.example.dbConnector.MysqlDBConnector.dbConnector;

public class ProcessAndAddToDB extends Thread {
    ArrayList<ObjectMapper> myArrayList=null;
    Connection conn=null;
    public ProcessAndAddToDB(ArrayList<ObjectMapper> paramObj,Connection conn)
    {
        this.myArrayList=paramObj;
        this.conn=conn;
    }
    @Override
    public void run() {

            String sql = "Update product_entity set image_url=\"hfhffgfgh\" where id=?";
            this.myArrayList.forEach((res) -> {
                try {
                    PreparedStatement prestmt = conn.prepareStatement(sql);
                    prestmt.setInt(1, res.getId());
                    prestmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

    }

}
