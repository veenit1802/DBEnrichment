package org.example.QueueImplementation;

import org.example.mapper.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class MyQueue {
    private Queue<ArrayList<ObjectMapper>> dbPoolRecord = null;
    static MyQueue myQueue = null;

    private MyQueue() {

    }

    public static MyQueue getInstance() {
        if (myQueue == null) {
            myQueue = new MyQueue();
            if (myQueue.dbPoolRecord == null) {
                myQueue.dbPoolRecord = new LinkedList<>();
            }
        }
        return myQueue;
    }

    public int getSize() {
        return dbPoolRecord.isEmpty() ? -1 : dbPoolRecord.size();
    }

    public void addRecordToDBPoolRecord(ArrayList<ObjectMapper> value) {

        dbPoolRecord.add(value);
    }

    public ArrayList<ObjectMapper> frontValue() {
        return dbPoolRecord.peek();
    }

    public ArrayList<ObjectMapper> removeFrontValue() {
        return dbPoolRecord.remove();
    }
}
