package com.netty.in.action;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/7
 **/
public class MyFetcher implements Fetcher {

    final  Data data;

    public MyFetcher(Data data){
        this.data = data;
    }
    @Override
    public void fetchData(FetcherCallback callback) {
        try{
            callback.onData(data);
        }catch (Exception e){
            callback.onError(e);
        }
    }
}
