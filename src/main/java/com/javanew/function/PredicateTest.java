package com.javanew.function;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/23
 **/
public class PredicateTest {

    public static void main(String[] args) {
        PredicateTest.eval(Arrays.asList(3, 2, 2, 3, 7, 3, 5),n->true);

    }


    public void test1(){

    }


    public static void eval(List<Integer> list, Predicate<Integer> predicate) {
        for(Integer n: list) {
            if(predicate.test(n)) {
                System.out.println(n + " ");
            }
        }
    }

}
