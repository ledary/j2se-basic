package com.basicdatabook.queue;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/31
 **/
public interface Queue {
    int getSize();
    boolean isEmpty();
    void enqueue(Object e);
    Object dequeue();
    Object peek();
}
