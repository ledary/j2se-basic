package com.basicdatabook.stack;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/31
 **/
public interface Stack {
    int getSize();
    boolean isEmpty();
    void push(Object e);
    Object pop();
    Object peek();
}
