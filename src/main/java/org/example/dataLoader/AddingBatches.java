package org.example.dataLoader;

import org.example.QueueImplementation.MyQueue;
import org.example.mapper.ObjectMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.dbConnector.MysqlDBConnector.dbConnector;
public class AddingBatches {
    private Connection conn=null;
    private static final Object lock = new Object();
    public void addData () throws SQLException {
        try{
            String query = "SELECT * FROM product_entity";
            this.conn = dbConnector();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int counter=0;
            MyQueue queue = MyQueue.getInstance();
            ArrayList<ObjectMapper> myArray = null;
            while (rs.next()) {
                    synchronized (lock){
                        try {
                            while (counter > 0 && queue.getSize() >= 2000) {
                                lock.wait(10);
                            }
                        }catch(Exception e)
                        {

                        }
                    }
                ObjectMapper objectMapper = new ObjectMapper(rs.getInt("id"),
                        rs.getString("image_url"),
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getFloat("product_price"),
                        rs.getString("product_type"));
                if(counter%100==0 && counter!=0) {
                    queue.addRecordToDBPoolRecord(myArray);
                    myArray=new ArrayList<>();
                    myArray.add(objectMapper);
                }
                else{
                    if(myArray==null) {
                        myArray= new ArrayList<>();
                    }
                    myArray.add(objectMapper);
                }
                counter++;
            }
            if(myArray!=null && myArray.size()>0) {
                queue.addRecordToDBPoolRecord(myArray);
            }

        }
        catch (Exception e)
        {

        }
    }

}
