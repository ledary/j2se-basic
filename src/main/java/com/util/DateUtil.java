package com.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/23
 **/
public class DateUtil {

    static public String format(Date date, String pattern) {
        pattern = checkStr(pattern);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    static public Object java8Format(LocalDateTime time, String pattern) {
        pattern = checkStr(pattern);
        String res = time.format(DateTimeFormatter.ofPattern(pattern));
        return res;
    }

    static protected String checkStr(String pattern) {
        if (Optional.of(pattern).isPresent() && pattern.length() == 0) {
            System.out.println("格式化串为空");
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        return pattern;
    }

    public static void main(String[] args) {
        HashMap<String,Object> map = new HashMap<>();
        Object obj = new Object();
        String a = "1";
        System.out.println(map.put(a,obj));
        System.out.println(map.put(a,obj));
    }
}
