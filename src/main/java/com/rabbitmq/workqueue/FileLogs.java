package com.rabbitmq.workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/18
 **/
public class FileLogs {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args)throws Exception {
        new FileLogs().test();
    }

    public void test()throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("ReceiveLogs2 Waiting for messages");
        Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("ReceiveLogs2 Received " + message);
            }
        };
        channel.basicConsume(queueName,true,consumer);
    }
}
