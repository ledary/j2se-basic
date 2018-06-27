package com.netty.in.action;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/7
 **/
public interface Fetcher {
    void fetchData(FetcherCallback callback);
}
