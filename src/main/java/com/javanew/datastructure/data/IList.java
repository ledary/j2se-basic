package com.javanew.datastructure.data;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/28
 **/
public interface IList<T> {
    /**
     * 是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 获取长度
     * @return
     */
    int length();
    /*
    根据索引获取元素
     */
    T get(int i);

    /**
     * 设置第i个元素的值为x
     * @param i
     * @param x
     */
    void set(int i,T x);

    /**
     * 移除第i个元素，并返回值
     * @param i
     * @return
     */
    T remove(int i);

    /**
     * 删除所有元素
     */
    void removeAll();
    void append(T x);
    //查询首次出现关键字为key的元素
    T search(T key);
    //在i位置插入某元素
    void insert(int i,T x);
    //插入元素  升序
    void insert(T x);
    //删除元素升序
    void remove(T x);
}
