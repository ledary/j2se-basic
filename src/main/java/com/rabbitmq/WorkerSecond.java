package com.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/18
 **/
public class WorkerSecond {

    public static void main(String[] args)throws Exception {
        new WorkerSecond().test();
    }
    private static final String TASK_QUEUE_NAME = "task_queue";
    public void test()throws Exception{
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        final Channel channel = conn.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);
        System.out.println("Worker1 Waiting for messages");
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("Worker1 Received " + message);
                try{
//                    throw new Exception();
                    doWork(message);
                }catch (Exception e){
                    channel.abort();
                }finally {
                    System.out.println("Worker1 Done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME,false,consumer);

    }
    private static void doWork(String task) {
        try {
            Thread.sleep(1000); // 暂停1秒钟
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
