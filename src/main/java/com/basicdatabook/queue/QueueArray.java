package com.basicdatabook.queue;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/31
 **/
public class QueueArray implements Queue {
//该队列实现的方式为
    private static final int CAP = 7;
    private Object[] elements;
    private int capacity;
    private int front;
    private int rear;

    public QueueArray(){
        this(CAP);
    }
    public QueueArray(int cap){
        capacity = cap + 1;
        elements = new Object[capacity];
        front = rear = 0;
    }

    @Override
    public int getSize() {
        return (rear - front + capacity)%capacity;
    }

    @Override
    public boolean isEmpty() {
        return front == rear;
    }

    @Override
    public void enqueue(Object e) {
        if(getSize() == capacity -1){
            expandSpace();
        }
        elements[rear] = e;
        rear = (rear+1)%capacity;
    }

//    //队首元素出队
    @Override
    public Object dequeue() {
        if(getSize()<1){
            throw new  IndexOutOfBoundsException("堆栈为空");
        }
        Object obj = elements[front];
        elements[front] = null;
        front = (front + 1)%capacity;
        return obj;
    }

    private void expandSpace(){
        Object[] a = new Object[elements.length * 2];
        int i = front;
        int j = 0;
        while (i!=rear){

            a[j++] = elements[i];
            i = (i+1)%capacity;
        }
        elements = a;
        capacity = elements.length;
        rear = j;
        front = 0;
    }

    @Override
    public Object peek() {

        if(getSize()>1){
            return  elements[front];
        }
        return null;

    }
}
