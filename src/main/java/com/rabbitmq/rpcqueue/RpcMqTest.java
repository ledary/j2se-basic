package com.rabbitmq.rpcqueue;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/21
 **/
public class RpcMqTest {
    public static void main(String[] args) throws Exception{
        System.out.println(new RpcClient().call("11"));
        System.out.println(new RpcClient().call("12"));

    }
//    public String test(){
//        try{
//            RpcClient client = new RpcClient();
//            return client.call("12");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }

}
