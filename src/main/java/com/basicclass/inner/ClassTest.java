package com.basicclass.inner;

import org.junit.Test;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/24
 **/
public class ClassTest {

    //测试静态内部类
    @Test
    public void test0(){
        InnerClass.InnerStatic iis1 = new InnerClass.InnerStatic();
        InnerClass.InnerStatic iis2 = new InnerClass.InnerStatic();
        System.out.println("iis1修改前" + iis1.getA());
        iis1.setA(10);
        System.out.println("iis1修改后" + iis1.getA());
        System.out.println("iis2修改后" + iis2.getA());
        iis2.setA(15);
        System.out.println("iis2修改后" + iis2.getA());

    }

//    测试非内部类
    @Test
    public void test2(){
        InnerClass.InnerUnStatic iius = new InnerClass().new InnerUnStatic();
        System.out.println(iius.innerMessage);
    }
}
