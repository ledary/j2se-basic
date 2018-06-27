package com.basicdatabook;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/31
 **/
public class LinkedListIterator implements  Iterator {
    private LinkedList list;
    private Node current;
    public LinkedListIterator(LinkedList list){
        this.list  = list;
        if(list.isEmpty()){
            current = null;
        }else{
            current = list.first();
        }

    }

    @Override
    public void first() {
        if(list.isEmpty()){
            current = null;
        }else{
            current = list.first();
        }
    }

    @Override
    public void next() {
        if(isDone()){
            throw new IndexOutOfBoundsException();
        }
        if(current == list.last()){
            current = null;
        } else{
            current = list.getNext(current);
        }
    }

    @Override
    public boolean isDone() {
        return current == null;
    }

    @Override
    public Object currentItem() {
        if(isDone()){
            throw new IndexOutOfBoundsException();
        }
        return current.getData();
    }

    @Override
    public boolean hasNext(){
        return null != current;
    }
}
