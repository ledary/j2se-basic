package com.javanew.datastructure.data;

/**
 * @author WGP
 * @description
 * @date 2018/5/28
 **/
public class Node<T> {
    public T data;
    public Node<T> next;
    public Node<T> head;
    public Node(){
        this(null,null);
    }
    public Node(T data,Node next){
        this.data = data;
        this.next = next;
    }


    public int length(){
        int i = 0;
        Node<T> p = this.head.next;
        while(p!= null){
            p = p.next;
            i++;
        }
        return i;
    }

    public T get(int i){
        if(i>=0){
            Node<T> p = this.head;
            for(int j =0;i<=i;j++){
                p = p.next;
            }
            if(p!=null){
                return p.data;
            }
        }
        return null;
    }

    public void insert(int i,T x){
        if(i >= 0){
            
        }
    }


}
