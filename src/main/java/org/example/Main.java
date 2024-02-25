package org.example;

import org.example.dataLoader.AddingBatches;
import org.example.processing.Validate;

import java.sql.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try {
                AddingBatches addingBatches = new AddingBatches();
                addingBatches.addData();
                return "hello";
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(()->{
            try {
                Validate validate = new Validate();
                validate.validator();
                return "hello";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.print(future.get());
                System.out.print(future2.get());
    }
}