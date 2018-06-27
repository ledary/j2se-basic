package com.basicdatabook;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/31
 **/
public interface Iterator {
     void first();
     void next();
     boolean isDone();
     Object currentItem();
     boolean hasNext();
}
