package com.basicdatabook;

import com.collection.HashSet;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/31
 **/
public class LinkedListDLNode implements LinkedList {
    private HashSet set = null;
    //当前链表的元素个数
    private int size;
    //头结点，只是起到头的作用
    private DLNode head;
    //尾结点
    private DLNode tail;
    public LinkedListDLNode(){
        size = 0;
        head = new DLNode();
        tail = new DLNode();
        head.setNext(tail);
        tail.setPre(head);
    }

    protected  DLNode checkPosition(Node p){
        if(p == null){
            System.out.println("错误，p为空");
        }
        if(p==head){
            System.out.println("错误，p为空");
        }
        if(p == tail){
            System.out.println("错误，p为空");
        }
        DLNode node = (DLNode)p;
        return node;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Node first() {
        if(isEmpty())return null;
        return head.getNext();
    }

    @Override
    public Node last() {
        if(isEmpty())return null;
        return tail.getPre();
    }

    @Override
    public Node getNext(Node p) {
        DLNode node = checkPosition(p);
        node = node.getNext();
        if(node == tail){
            return null;
        }
        return node;
    }

    @Override
    public Node geetPre(Node p) {
        DLNode node = checkPosition(p);
        if(node == head){

        }
        return node;
    }

    @Override
    public Node insertFirst(Object e) {
        DLNode node = new DLNode(e,head,head.getNext());
        head.getNext().setPre(node);
        head.setNext(node);
        node.setPre(head);
        size ++;
        return node;
    }

    @Override
    public Node insertLast(Object e) {
        DLNode node = new DLNode(e,tail.getPre(),tail);
        tail.getPre().setNext(node);
        tail.setPre(node);
        size ++;
        return node;
    }

    @Override
    public Node insertAfter(Node p, Object e) {
        DLNode node = checkPosition(p);
        DLNode m = new DLNode(e,node,node.getNext());
        node.getNext().setPre(m);
        node.setNext(m);
        size ++;
        return m;
    }

    @Override
    public Node insertBefore(Node p, Object e) {
        DLNode node = checkPosition(p);
        DLNode m = new DLNode(e,node.getPre(),node);
        node.getPre().setNext(m);
        node.setPre(m);
        size ++;
        return m;
    }

    @Override
    public Object remove(Node p) {
        DLNode node = checkPosition(p);
        Object obj = node.getData();
        node.getPre().setNext(node.getNext());
        node.getNext().setPre(node.getPre());
        size -- ;
        return obj;
    }

    @Override
    public Object removeFirst() {
        return remove(head.getNext());
    }

    @Override
    public Object removeLast() {
        return remove(tail.getPre());
    }

    @Override
    public Object replace(Node p, Object e) {
        DLNode node = checkPosition(p);
        Object obj = node.getData();
        node.setData(e);
        return obj;
    }

    @Override
    public Iterator elements(){
        return new LinkedListIterator(this);
    }

}
