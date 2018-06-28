package com.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/17
 **/
public class Customer {
    public final static String QUEUE_NAME = "rabbitMQ.test";

    public static void main(String[] args) throws  Exception{
        Customer cou = new Customer();
        cou.testCustomer();
    }


    public void testCustomer()throws  Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "Coustomer Waiting Received messages ";

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String (body,"UTF-8");
                System.out.println("Customer Received "+ message);
            }
        };

        channel.basicConsume(QUEUE_NAME,true,consumer);

    }

}
