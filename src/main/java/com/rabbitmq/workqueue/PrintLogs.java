package com.rabbitmq.workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/18
 **/
public class PrintLogs {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args)throws Exception {
        new PrintLogs().test();
    }

    public void test()throws Exception{
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置连接地址
        factory.setHost("127.0.0.1");
        //创建连接
        Connection conn = factory.newConnection();
        //获取连接时的通道
        Channel channel = conn.createChannel();
        //声明交换机的类型 以及交换机的名字  fanout类型的交换机，可以把一条消息发送给不同 到客户端，
        //针对同一条消息，采用不同方式进行处理的场景。
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //声明队列，并获取队列 的名字
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("ReceiveLogs1 Waiting for messages");
        Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("ReceiveLogs1 Received " + message);
            }
        };
        channel.basicConsume(queueName,true,consumer);
    }
}
