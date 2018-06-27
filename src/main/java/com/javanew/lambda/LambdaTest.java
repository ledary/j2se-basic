package com.javanew.lambda;

import org.junit.Test;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/23
 **/
public class LambdaTest {


    @Test
    public void test(){
        //jdk8以上的写法
        MathOperation add = new MathOperation() {
            @Override
            public int operation(int a, int b) {
                return a+b;
            }
        };
        MathOperation addition =(int a,int b)-> a+b;
        MathOperation substraction = (a,b)->a-b;
        MathOperation multiplication = (int a,int b)->{
            return a*b;
        };
        MathOperation divsion = (int a,int b)-> a/b;
        System.out.println(operate(10,5,addition));
        System.out.println(operate(10,5,add));
        System.out.println(operate(10,5,substraction));
        System.out.println(operate(10,5,multiplication));
        System.out.println(operate(10,5,divsion));
    }










    // 内部接口
    interface MathOperation
    {
        int operation(int a, int b);
    }

    interface GreetingService
    {
        void sayMessage(String message);
    }

    interface RunableCoustom
    {
        void run();
    }

    private int operate(int a, int b, MathOperation mathOperation)
    {
        return mathOperation.operation(a, b);
    }
}
