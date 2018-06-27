package com.javanew.datetime;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/23
 **/
/*
新旧时间类型的转换
新旧格式化
与时间戳的转换
与毫秒的转换
 */
public class LocalDateTest {

    //新特性时间加减的计算
    @Test
    public void testday(){
        LocalDate today = LocalDate.now();
        LocalDate yestday = today.plusDays(-1);
        System.out.println("today:" + today);
        System.out.println("yestday:" + yestday);
    }

    /**
     *   1、date类型向新类型的转化
     */
    @Test
    public void test0(){
        java.util.Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
        Instant instant = date.toInstant();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant,ZoneId.systemDefault());
        System.out.println("秒：" + localDateTime.getSecond());
        System.out.println("java8以上：" + format.format(date));
        System.out.println("java8：" + localDateTime.format(DateTimeFormatter.ofPattern("HH-MM-dd HH:mm:ss",Locale.ROOT)));
        System.out.println("java8："+ localDateTime.getYear() + " " + localDateTime.getMonthValue() + " " + localDateTime.getDayOfMonth());


    }

    /**
     * 新特性转Date
     */
    @Test
    public void test3(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        System.out.println(localDateTime);
        System.out.println(date);
    }

    //毫秒数转时间  时间转毫秒
    @Test
    public void test1(){
        LocalDateTime time=LocalDateTime.now();
        System.out.println(time);
        Instant instant = time.atZone(ZoneId.systemDefault()).toInstant();
        long sa = instant.toEpochMilli();
        System.out.println(sa);
        System.out.println(Instant.ofEpochMilli(sa).atZone(ZoneId.systemDefault()).toLocalDateTime());
//        时间转毫秒数/时间戳:
//        结果：
//        2018-05-23T19:32:37.844
//        1527075157844
//        2018-05-23T19:32:37.844
    }


    //时间转换为时间戳
    @Test
    public void testa()throws Exception{
        String s = "";
        String res = "2018-01-01 12:34:33:666";
        Date dd = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        s =simpleDateFormat.format(dd);
        System.out.println(dd);
        System.out.println(s);
//        结果：
//        Wed May 23 19:31:39 CST 2018
//        2018-05-23 19:31:39:062
    }

    //时间戳的计算
    @Test
    public void test2(){
       long time =   LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Long newSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println("time：" + time);
        System.out.println("time2: " + newSecond);
//        结果：
//        time：1527066286296
//        time2: 1527066286
    }



//    新类型向旧类型的转化
    @Test
    public void testConvert(){
        LocalDateTime today = LocalDateTime.now();
        Instant instant = today.atZone(ZoneId.systemDefault()).toInstant();
        Date date  =  Date.from(instant);
        System.out.println("Date:" + date);
        System.out.println("localdatetime:" + today);
    }

    //Local的使用，控制语言环境
    public void test4(){
        LocalDateTime today = LocalDateTime.now();
        String localTime = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss",Locale.GERMAN));
        System.out.println("localTime：" + localTime);
        String enTime = today.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println("english: " + enTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = today.atZone(zone).toInstant();
        System.out.println(Date.from(instant));
    }



}
