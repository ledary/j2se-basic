package com.basicdatabook;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/30
 **/
public class SLNode {
    private Object element;
    private SLNode next;
    public SLNode(){
        this(null,null);
    }

    public SLNode(Object e,SLNode next){
        this.element = e;
        this.next = next;
    }

    public void setNext(SLNode next){
        this.next  = next;
    }

    public SLNode getNext(){
        return next;
    }

    public Object getData(){
        return element;
    }

    public void setData(Object obj){
        element = obj;
    }
}
