package com.javanew.function;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/23
 **/

//Java 8为函数式接口引入了一个新注解@FunctionalInterface，
//        主要用于编译级错误检查，加上该注解，当你写的接口不符合函数式接口定义的时候，编译器会报错。
@FunctionalInterface
public interface GreetingService {
    void sayMessage(String message);

    default String doSomeMoreWork(String str){
        System.out.println("Hello World");
        return str;
    }
    static void sendMessage(String message){
        System.out.println("接口静态方法" + message);
    }
}
