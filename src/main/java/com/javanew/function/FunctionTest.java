package com.javanew.function;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/23
 **/
public class FunctionTest {
    //消费型接口
    //void accept(T t)
    @Test
    public void test1(){
        Consumer<List<Integer>> consumer = n-> n.forEach(el->{
            System.out.println(el);
        });
        happy(Arrays.asList(1,2,3,4,5),consumer);

    }
    //提供者接口
    //T get()
    @Test
    public void test2(){
       Supplier<Integer> sup = ()->5;
        List<Integer> numList = getNumList(8,sup);
        numList.forEach(el->{
            System.out.println("sup.get() + 8 = " + el);
        });
    }

    @Test
    public void test3(){

//        Integer a = 6;
        Integer  a = 6;
        //断言判断是否生成数据
//        Predicate pre = n->n instanceof  Integer;
        Predicate pre = n->n instanceof  String;
        //生产数据
        Supplier<Integer> sup = ()->a;

        //处理数据
        Function<List<Integer>,List<String>> fun = n->{
            if(Optional.ofNullable(n).isPresent()){
                List<String> list = new ArrayList<>(n.size());
                n.forEach(el->{
                    el += el;
                    list.add(el.toString());
                });
                return list;
            }else{
                System.out.println("list为空");
                return new ArrayList<>();
            }
        };

//        消费数据
       Consumer<List<String>> com = n->{
           n.forEach(el->{
               System.out.println("(" + a + "+ 6) * 2 = " + el);
           });
       };
       com.accept(fun.apply( pre.test(a)?getNumList(6,sup):null));

    }

    @Test
    public void test4(){
        String newStr=strHandler("/t/t/t 我/t/t/t/t最威武  ", (str)->str.trim());
        System.out.println(strHandler("我最威武",(str) -> str.substring(2,5)));
    }

    //消费型方法
    public void happy(List<Integer> list,Consumer<List<Integer>> con){
        con.accept(list);
    }
    //函数型方法
    public List<String> strHandler(List<Integer> list, Function<List<Integer>,List<String>> fun){
        return  fun.apply(list);
    }

    //提供者方法
    public List<Integer> getNumList(int addInt,Supplier<Integer> sup){
        Integer a = sup.get() + addInt;
        return Arrays.asList(a,a,a,a,a,a);
    }


    //需求:用于处理字符串
    public String strHandler(String str, Function<String,String> fun){
        fun.apply(str);
        return fun.apply(str);
    }
}

