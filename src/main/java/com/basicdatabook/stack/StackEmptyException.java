package com.basicdatabook.stack;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/31
 **/
public class StackEmptyException extends  RuntimeException {
    public StackEmptyException(String err){
        super(err);
    }
}
