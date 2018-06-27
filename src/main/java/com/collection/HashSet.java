package com.collection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/24
 **/
public class HashSet<E> extends AbstractSet<E> implements Set<E>,Cloneable,Serializable {

    static final long serialVersionUID = -5024744406713321676L;
//    private java.util.HashSet hs = new java.util.HashSet();

    //由此可以知道，HashSet是基于HashMap实现的
    private transient HashMap<E,Object>  map;
    //针对map的value值，这个值是一个虚拟值
    private static final Object PRESENT = new Object();

    public HashSet(){
        map = new HashMap<>();
    }

    //带有初始化大小和增长因子的hashMap
    public HashSet(int initialCapacity,float loadFactor){
        map = new HashMap<>(initialCapacity,loadFactor);
    }

    public HashSet(Collection<? extends  E> c){
        map = new HashMap<>(Math.max((int)(c.size()/.75f)+1,16));

    }

    public HashSet(int initialCapacity){
        map = new HashMap<>(initialCapacity);
    }

    HashSet(int initialCapacity,float loadFactor,boolean dumy){
//        map = new LinkedHashMap<>(initialCapacity,loadFactor);
    }

    @Override
    public Iterator<E> iterator(){
        return map.keySet().iterator();
    }
    @Override
    public int size(){
        return map.keySet().size();
    }

    @Override
    public boolean isEmpty(){
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o){
        return map.containsKey(o);
    }

    //map里put新元素时，会返回null。所以加上null的判断。
    @Override
    public boolean add(E e){
        return map.put(e,PRESENT) == null;
    }

    @Override
    public boolean remove(Object o){
        return  map.remove(o) == PRESENT;
    }

    @Override
    public void clear(){
        map.clear();
    }

    /**
        * 返回此HashSet实例的浅表副本：并没有复制这些元素本身。
       *
      * 底层实际调用HashMap的clone()方法，获取HashMap的浅表副本，并设置到  HashSet中。
        */
    @Override
    public Object clone(){
        try{
            HashSet<E> newSet = (HashSet<E>)super.clone();
//            newSet.map = (HashMap<E,Object>)map.clone();
            return newSet;
        }catch (CloneNotSupportedException e){
            throw new InternalError();
        }
    }
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        // Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out HashMap capacity and load factor
        //因为这两个方法是default类型的，所以无法访问
//        s.writeInt(map.capacity());
//        s.writeFloat(map.loadFactor());

        // Write out size
        s.writeInt(map.size());

        // Write out all elements in the proper order.
        for (E e : map.keySet()) {
            s.writeObject(e);
        }
    }

    @Override
    public Spliterator<E> spliterator(){
        //同包类型静态内部类，无法访问
//        return new HashMap.KeySpliterator<E,Object>(map, 0, -1, 0, 0);
        return null;
    }







}
