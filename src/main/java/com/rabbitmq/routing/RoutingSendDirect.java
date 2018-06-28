package com.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/18
 **/
public class RoutingSendDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    private static final String[] routingKeys = new String[]{
            "info","warning","error"};

    public void test()throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        for(String routingKey:routingKeys){
            String message = "RoutingSendDirect Send the message level:"
                    + routingKey;
            channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes());
            System.out.println("RoutingSendDirect Send" + routingKey +":" + message);
        }
        channel.close();
        conn.close();
    }

}
