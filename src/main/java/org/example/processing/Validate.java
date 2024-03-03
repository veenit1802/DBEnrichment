package org.example.processing;

import org.example.QueueImplementation.MyQueue;
import org.example.mapper.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.example.dbConnector.PostgresqlDBConnection.dbPgConnector;

public class Validate {
    private static final Logger logger = LoggerFactory.getLogger(Validate.class);
    public static Object lock = new Object();

    public void validator() throws InterruptedException {
        Date date = new Date();
        MyQueue queue = MyQueue.getInstance();
        Connection conn = dbPgConnector();
        int retry = 10;
        while (retry > 0) {
            synchronized (lock) {
                try {
                    while (queue.getSize() == -1 && retry > 0) {
                        retry--;
                        lock.wait(160);
                    }
                } catch (Exception e) {
                }
            }

            if (retry > 0) {
                retry = 10;
                int maxThread = 50;
                ExecutorService executorService = Executors.newFixedThreadPool(50);
                Thread[] threads = new Thread[maxThread];
                while (queue.getSize() != -1) {
                    ArrayList<ObjectMapper> myArray = queue.frontValue();
                    queue.removeFrontValue();
                    executorService.submit(() -> {
                        ProcessAndAddToDB processAndAddToDB = new ProcessAndAddToDB(myArray, conn);
                        processAndAddToDB.run();
                    });
//                    threads[maxThread-1] = new ProcessAndAddToDB(myArray,conn);
//                    threads[maxThread-1].start();
//                    maxThread--;
                }

//                try {
//                    for (Thread thread : threads) {
//                        if (thread != null) { // Check for null before joining
//                            thread.join();
//                        }
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                executorService.shutdown();

            }
        }
        logger.info("End Time {}", date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
    }
}
