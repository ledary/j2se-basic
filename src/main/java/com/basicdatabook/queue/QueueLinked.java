package com.basicdatabook.queue;

import com.basicdatabook.SLNode;

/**
 * @author WGP
 * @description
 * @date 2018/6/2
 **/
public class QueueLinked implements Queue {
    private SLNode front;
    private SLNode rear;
    private int size;


    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enqueue(Object e) {
        SLNode node = new SLNode(e,null);
        rear.setNext(node);
        rear = node;
        size ++;
    }

    //去除栈顶元素
    @Override
    public Object dequeue() {
        SLNode p = front.getNext();
        front.setNext(p.getNext());
        size --;
        if(size < 1){
            rear = front;
        }
        return front.getData();
    }

    @Override
    public Object peek() {
        if(size<1){
            throw new QueueEmptyException("队列为空");
        }
        return front.getNext().getData();
    }
}
