package com.rabbitmq.topicmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/19
 **/
public class ReceiveLogsTopic1 {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args)throws Exception {
        new ReceiveLogsTopic1().test();
    }

    public void test() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();
        String[] routingKeys = new String[]{"*.orange.*"};
        for (String key : routingKeys) {
            channel.queueBind(queueName, EXCHANGE_NAME, key);
            System.out.println("ReceiveLogsTopic1 exchange:" + EXCHANGE_NAME + ", queue:" + queueName + ", BindRoutingKey:" + key);
        }
        System.out.println("ReceiveLogsTopic1 Waiting for messages");
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("ReceiveLogsTopic1 Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName,true,consumer);
    }



}
