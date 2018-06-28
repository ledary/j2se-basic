package com.rabbitmq.routing;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/18
 **/
public class ReceiveLogsDirect1 {

    private static final String EXCHANG_NAME = "direct_logs";

    private static final String[] routingKeys = new String[]{
            "info","warning","error"};

    public void test()throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANG_NAME,"direct");
     String queueName = channel.queueDeclare().getQueue();
     for(String routingKey:routingKeys){
         channel.queueBind(queueName,EXCHANG_NAME,routingKey);
         System.out.println("ReceiveLogsDirect1 exchange:"+EXCHANG_NAME+"," +
                 " queue:"+queueName+", BindRoutingKey:" + routingKey);
     }
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("ReceiveLogsDirect1 Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName,true,consumer);
    }


}
