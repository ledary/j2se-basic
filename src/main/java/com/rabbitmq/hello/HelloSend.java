package com.rabbitmq.hello;



import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/10
 **/
public class HelloSend {

    private final static String QUEUE_NAME = "hello";
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for(int i=0;i<5;i++){
            String message =i +" "+ "发送第" + i + "条" + "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" Sent '" + message + "'");
        }


        channel.close();
        connection.close();
    }



}
