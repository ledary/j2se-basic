package com.rabbitmq.rpcqueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * @author WGP
 * @description
 * @date 2018/5/21
 **/
public class RpcClient {

    private Connection conn;
    private Channel channel;
    private String requestQueueName = "pc_queue";
    private String replyQueueName;

    public RpcClient()throws IOException,TimeoutException{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        conn = factory.newConnection();
        channel = conn.createChannel();
        replyQueueName = channel.queueDeclare().getQueue();
    }

    public String call(String message )throws IOException,InterruptedException{
        final String corrId = UUID.randomUUID().toString();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();
        channel.basicPublish("",requestQueueName,props,message.getBytes());
        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);
        channel.basicConsume(replyQueueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if(properties.getCorrelationId().equals(corrId)){
                    response.offer(new String(body,"UTF-8"));
                }
            }
        });
        return response.take();
    }
}
