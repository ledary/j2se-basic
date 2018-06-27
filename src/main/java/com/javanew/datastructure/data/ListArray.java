package com.javanew.datastructure.data;

import java.util.ArrayList;

/**
 * @author WGP
 * @description
 * @date 2018/5/29
 **/
public class ListArray<T> implements List<T> {

    private ArrayList a;
    private Object[] elements;
    private int size;
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0?true:false;
    }

//    O(n)
    @Override
    public boolean contains(T e) {
        if(e == null){
            throw new  NullPointerException();
        }
        for(int i = 0;i<size;i++){
            if(e == this.elements[i] || e.equals(this.elements[i])){
                return true;
            }
        }
        return false;
    }
    //    O(n)
    @Override
    public int indexOf(T e) {
        if(e == null){
            throw new  NullPointerException();
        }
        for(int i = 0;i<size;i++){
            if(e == this.elements[i] || e.equals(this.elements[i])){
                return i;
            }
        }
        return -1;
    }

    //    O(n)
    @Override
    public void insert(int i, T e) {
        if(i <0 || i<this.size){
            throw new IndexOutOfBoundsException();
        }
        //这里得判断元素个数是否和数组的长度相等，相等了的话需要进行扩容
        if(size>=elements.length){
            resize();
        }
        for(int j= size;j>i;j--){
            elements[j+1] = elements[j];
        }
        elements[i] = e;
    }

    //    O(n)
    private void resize(){
        Object [] ts = new Object[elements.length << 1];
        for(int i = 0;i<size;i++){
            ts[i] = elements[i];
        }
        elements = ts;
    }

    @Override
    public boolean insertBefore(T target, T source) {
        int i = indexOf(source);
        if(i<0){
            return false;
        }
        insert(i,target);
        return false;
    }

    @Override
    public boolean insertAfter(T target, T source) {
        int i = indexOf(source);
        if(i<0){
            return false;
        }
        insert(i+1,target);
        return false;
    }

    @Override
    public T remove(int i) {
        if(i<0 || i>size){
            throw new IndexOutOfBoundsException();
        }
        Object obj = elements[i];
        for(int j = i;j<size;j++){
            elements[j] = elements[j+1];
        }
        elements[--size] = null;
        return (T)obj;
    }

    /**
     * 删除线性表中 第一个与e相同的元素
     * @param t
     * @return
     */
    @Override
    public boolean remove(T t) {
        int i = indexOf(t);
        if(i<0){
            return false;
        }
        remove(i);
        return true;
    }

    @Override
    public T replace(int i, T t) throws IndexOutOfBoundsException {
        if(i<0 || i>size){
            throw new IndexOutOfBoundsException();
        }
        Object obj = elements[i];
        elements[i] = t;
        return (T)obj;
    }

    @Override
    public T get(int i) throws IndexOutOfBoundsException {
        if(i<0 || i>=size){
            throw new IndexOutOfBoundsException();
        }
        return (T)elements[i];
    }
}
