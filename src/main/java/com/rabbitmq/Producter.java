package com.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/17
 **/
public class Producter {

    public final static String QUEUE_NAME = "rabbitMQ.test";

    public static void main(String[] args) throws Exception {
        Producter pro = new Producter();
        pro.testProducter();
    }
    public void testProducter()throws  Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "Hello RabbitMQ";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
        System.out.println("Producter Send " + message );
//        channel.close();
//        conn.close();
    }



}
