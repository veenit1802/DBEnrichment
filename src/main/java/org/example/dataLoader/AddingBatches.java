package org.example.dataLoader;

import org.example.QueueImplementation.MyQueue;
import org.example.mapper.ObjectMapper;

import java.sql.*;
import java.util.ArrayList;

import static org.example.dbConnector.MysqlDBConnector.dbConnector;

public class AddingBatches {
    private Connection conn = null;
    private static final Object lock = new Object();

    public void addData() throws SQLException {
        try {
            int offset = 0;
            this.conn = dbConnector();
            int rowCount = 0;
            MyQueue queue = MyQueue.getInstance();
            while (true) {
                String query = "SELECT * FROM product_entity limit 600000 offset ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, offset);
                ResultSet rs = stmt.executeQuery();

                int counter = 0;
                offset += 600000;

                ArrayList<ObjectMapper> myArray = null;

                while (rs.next()) {
                    synchronized (lock) {
                        try {
                            while (counter > 0 && queue.getSize() >= 2000) {
                                lock.wait(10);
                            }
                        } catch (Exception e) {

                        }
                    }
                    rowCount += 1;
                    ObjectMapper objectMapper = new ObjectMapper(rs.getInt("id"),
                            rs.getString("image_url"),
                            rs.getString("product_id"),
                            rs.getString("product_name"),
                            rs.getFloat("product_price"),
                            rs.getString("product_type"));
                    if (counter % 3000 == 0 && counter != 0) {
                        queue.addRecordToDBPoolRecord(myArray);
                        myArray = new ArrayList<>();
                        myArray.add(objectMapper);
                    } else {
                        if (myArray == null) {
                            myArray = new ArrayList<>();
                        }
                        myArray.add(objectMapper);
                    }
                    counter++;
                }
                if (myArray != null && myArray.size() > 0) {
                    queue.addRecordToDBPoolRecord(myArray);
                }
                if (counter == 0) {
                    break;
                }
            }
        } catch (Exception e) {

        }
    }

}
