package com.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/18
 **/
public class NewTask {


    public static void main(String[] args)throws Exception {
        new NewTask().test();
    }
    private static final String TASK_QUEUE_NAME = "task_queue";

    //建立工厂
//    建立连接
//    建立通道
//     交换器
//    队列
    public void test()throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);
        for(int i=0;i<10;i++){
            String message = "Hello RabbitMQ " + i;
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("NewTask send " + message);
        }
        channel.close();
        conn.close();

    }
}
