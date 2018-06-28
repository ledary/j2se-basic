package com.rabbitmq.hello;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/10
 **/
public class HelloReceive {
    private final static String QUEUE_NAME = "hello";




    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println( message );
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
