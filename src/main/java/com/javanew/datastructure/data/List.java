package com.javanew.datastructure.data;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/29
 **/
public interface List<T> {
    /*
    获取数组大小
     */
    int size();
    /*
    判断是否为空
     */
    boolean isEmpty();
    /*
    是否包含某元素
     */
    boolean contains(T e);
    /*
    返回该元素在线性表的序号
     */
    int indexOf(T o);
    /*
    插入元素到i位置
     */
    void insert(int i,T e);
    /*
    插入元素到另一个元素之前
     */
    boolean insertBefore(T target ,T source);
    /*
    插入元素到另一个元素之后
     */
    boolean insertAfter(T target,T source);
    /*
    删除i位置的元素，并返回该元素
     */
    T remove(int i);
    /*
    删除该元素 返回成功/失败
     */
    boolean remove(T t);
    /*
    替换i位置的元素为t
     */
    T replace(int i,T t);
    /*
    获取i位置的元素
     */
    T get(int i);



}
