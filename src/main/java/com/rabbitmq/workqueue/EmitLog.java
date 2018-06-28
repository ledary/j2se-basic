package com.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author WGP
 * @description     工作队列  fanout方式
 * @date 2018/5/18
 **/
public class EmitLog {
    private static final  String EXCHANGE_NAME = "logs";


    public static void main(String[] args)throws Exception {
        new EmitLog().test();
    }
    public void test()throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
//        for(int i=0;i<5;i++){
            String message = "Hello World " ;
            //交换机名称   队列名称
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("EmitLog Sent " + message);
//        }
        channel.close();
        conn.close();
    }
}
