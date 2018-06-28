package com.rabbitmq.delayqueue;


import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;

/**
 * Created by wgp on 2018/6/27.
 */
public class DelayRecv {
    private final  String EXCHANGE_NAME2 = "exchange_direct";

    private final String ROUTE_KEY = "routekey";


    public void delayRecv()throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME2,BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,EXCHANGE_NAME2,ROUTE_KEY);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = SerializationUtils.deserialize(body);
                System.out.println(envelope.getRoutingKey() + ":delay Received：" + message + " :done");

            }
        };

        channel.basicConsume(queueName,true,consumer);
    }

    public static void main(String[] args)throws Exception {
        new DelayRecv().delayRecv();
    }
}
