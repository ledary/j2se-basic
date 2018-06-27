package com.javanew.stream;

import com.sun.xml.internal.ws.encoding.StringDataContentHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/24
 **/
/*
对集合对象功能增强
方便对集合进行各类操作
过滤，最大值，最小值，统计
穿行，并行 fork/join框架
 */

/*
特性
不是一个数据结构
为lambda表达式设计
不支持索引访问
很方便的作为数组或集合输出
支持惰性访问
并行计算

 */
public class StreamTest {



    @Test
    public void test0(){
        List<String> list = Arrays.asList("a1","a2","a3","a4","a5","a6");
        list.stream().filter(s->s.startsWith("a"))
                .map(String::toUpperCase)
                .sorted(String::compareTo)
                .forEach(System.out::println);
    }

    //Stream.of生成
    public void test1(){
        Stream<Integer> stream = Stream.of(1,2,3,4,5);
        stream.forEach(System.out::println);
    }

    //Arrays.stream
    public void test2(){
        Stream<Integer> stream = Arrays.stream(new Integer[]{1,2,3,4,5});
        stream.forEach(System.out::println);
    }

    //使用Collection.stream() or Collection.parallelStream()
    @Test
    public void test3(){
        List<Integer> list = new ArrayList<>(Arrays.asList(new Integer[]{1,2,3,4,5}));
        Stream<Integer> stream = list.stream();
        Stream<Integer> integerStream = list.parallelStream();

        stream.forEach(System.out::println);
        integerStream.forEach(System.out::println);
    }

    //IntStream.range
    @Test
    public void test4(){
        IntStream stream = IntStream.range(1,10);
        IntStream stream1 = IntStream.rangeClosed(1,10);
        stream.forEach(System.out::println);
        stream1.forEach(System.out::println);
    }

    @Test
    public void test5(){

            Stream<Integer> stream = Stream.generate(() -> {
                int i=0;
                try
                {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                return i++;
            });
            stream.forEach(p -> System.out.println(p));
        }

        @Test
        public void test6(){
            List<Integer> list = new ArrayList<>(Arrays.asList(new Integer[]{1,2,3,4,5}));
            Stream<Integer> stream = list.stream();
//            List<Integer> eventNumberList = stream.filter(i->i>2).collect(Collectors.toList());
//            eventNumberList.forEach(System.out::println);

         Optional<Integer> a  = stream.filter(i->i<6).reduce((x, y)->x+y);
            System.out.println(a.get());
        }




}
