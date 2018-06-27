package com.basicdatabook;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/31
 **/
public interface LinkedList  {
     int getSize();

     boolean isEmpty();

//     返回第一个节点
     Node first();
//返回最后一个节点
     Node last();
//获取p之后的节点
     Node getNext(Node p);
//获取p之前的结点
     Node geetPre(Node p);
//    插入第一个结点
     Node insertFirst(Object e);
//    把结点插入到最后
     Node insertLast(Object e);
     Node insertAfter(Node p,Object e);

     Node insertBefore(Node p,Object e);
     Object remove(Node p);
     Object removeFirst();
     Object removeLast();
     Object replace(Node p,Object e);
     Iterator elements();
}
