package com.javanew.datastructure.data;

import java.util.LinkedList;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/29
 **/
public class ListLinked implements List {
    private int size;
    private SLNode head;

    public ListLinked() {
        head = new SLNode();
        size = 0;
    }

    /**
     * 获取元素e所在节点的前驱节点
     *
     * @param e
     * @return
     */
    private SLNode getPreNode(Object e) {
        SLNode p = head;
        while (p.getNext() != null) {
            if (p.getNext() == e || p.getNext().equals(e)) {
                return p;
            }
            p = p.getNext();
        }
        return null;
    }

    //辅助方法，根据索引 获取该位置的前驱节点
    private SLNode getPreNode(int i) {
        SLNode p = head;
        for (; i > 0; i--) {
            p = p.next;
        }
        return p;
    }

    private SLNode getNode(int i){
        SLNode p = head.next;
        for(;i>0;i--){
            p = p.next;
        }
        return p;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {

        return size ==0;
    }

    @Override
    public boolean contains(Object e) {
       SLNode p = head.next;
       while(p!=null){
           if(p == e || p.equals(e)){
               return true;
           }
           p = p.next;
       }
        return false;
    }

    @Override
    public int indexOf(Object o) {
        SLNode p = head.next;
        int index = 0;
        while(p!= null){
            if(p == o || p.equals(o)){
                return index;
            }
            index++;
            p = p.next;
        }
        return index >=0?index:-1;
    }

    @Override
    public void insert(int i, Object e) {
        SLNode p = head.next;
        int index = 0;
        while(p!= null && index != i){
            index++;
            p = p.next;
        }
        SLNode q = new SLNode(e,p.getNext());
        p = q;

    }

    @Override
    public boolean insertBefore(Object target, Object source) {
        return false;
    }

    @Override
    public boolean insertAfter(Object target, Object source) {
        return false;
    }

    @Override
    public Object remove(int i) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public Object replace(int i, Object o) {
        return null;
    }

    @Override
    public Object get(int i) {
        return null;
    }

    private static class SLNode<E> {
        E item;
        SLNode<E> next;

        public SLNode() {
            this(null, null);
        }

        public SLNode(E element, SLNode<E> next) {
            this.item = element;
            this.next = next;
        }

        public E getItem() {
            return item;
        }

        public void setItem(E item) {
            this.item = item;
        }

        public SLNode<E> getNext() {
            return next;
        }

        public void setNext(SLNode<E> next) {
            this.next = next;
        }
    }
}
