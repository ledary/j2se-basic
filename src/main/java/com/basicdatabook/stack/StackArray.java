package com.basicdatabook.stack;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/31
 **/
public class StackArray implements Stack {
    private final int LEN = 8;
    private Object[] elements;
    private int top;

    public StackArray(){
        top = -1;
        elements = new Object[LEN];
    }



    @Override
    public int getSize() {
        return top + 1;
    }

    @Override
    public boolean isEmpty() {
        return top <0 ;
    }

    @Override
    public void push(Object e) {
        if(getSize() >= elements.length){
            expandSpace();
        }else{
            elements[++top] = e;
        }
    }

    private void expandSpace(){
        Object[] a = new Object[elements.length * 2];
        for(int i =0;i<elements.length;i++){
            a[i] = elements[i];
        }
        elements = a;
    }

    @Override
    public Object pop() {
        if(getSize() <1){
            throw new StackEmptyException("堆栈为空");
        }
        Object obj = elements[top];
//         这里注意top--和--top的区别
        elements[top--] = null;
        return obj;
    }

    @Override
    public Object peek() {
        if(getSize() < 1){
            throw  new StackEmptyException("堆栈为空");
        }
        return elements[top];
    }
}
