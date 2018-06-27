package com.basicdatabook.queue;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/31
 **/
public class QueueEmptyException extends RuntimeException {
    public QueueEmptyException(String err){
        super(err);
    }
}
