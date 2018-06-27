package com.netty.in.action.futures;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/7
 **/
public class FutureExample {


    public static void main(String[] args)throws  Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("i am task1");
            }
        };
        Callable<Integer> task2 = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Integer(100);
            }
        };
        Future<?> f1 = executor.submit(task1);
        Future<Integer> f2 = executor.submit(task2);
        System.out.println("task1 is completed" + f1.isDone());
        System.out.println("task2 is completed" + f2.isDone());
        while (f1.isDone()){
            System.out.println("task1 completed" );
            break;
        }
        while (f2.isDone()){
            System.out.println("return value by task2：" + f2.get());
            break;
        }
    }


}
