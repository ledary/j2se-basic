package com.netty.in.action;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/7
 **/
public interface FetcherCallback {

    void onData(Data data)throws Exception;
    void onError(Throwable cause);
}
