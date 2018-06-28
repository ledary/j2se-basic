package com.rabbitmq.rpcqueue;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/21
 **/
public class RpcServer {

    private static final String RPC_QUEUE_NAME = "pc_queue";

    public static int fib(int n){
        if(n == 0){
            return 0;

        }
        if(n == 1){
            return 1;
        }
        return fib(n-1) + fib(n-1);
    }

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = null;
        try{
            conn = factory.newConnection();
            final Channel channel = conn.createChannel();
            channel.queueDeclare(RPC_QUEUE_NAME,false,false,false,null);
            channel.basicQos(1);
            System.out.println("[x] Awaiting rpc requests");
            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("接收到消息了");
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties.
                            Builder().
                            correlationId(properties.getCorrelationId()).build();

                    String response = "";
                    try{
                        String message = new String(body,"UTF-8");
                        int n = Integer.parseInt(message);
                        System.out.println("[.] fib(" + message + ")");
                        response +=fib(n);
                    }catch (RuntimeException ex){
                        System.out.println("[.] " + ex.toString());
                    }finally {
                        channel.basicPublish("",properties.getReplyTo(),replyProps,response.getBytes("UTF-8"));
                        channel.basicAck(envelope.getDeliveryTag(),false);
                        synchronized (this){
                            this.notify();
                        }
                    }
                }
            };
            channel.basicConsume(RPC_QUEUE_NAME,false,consumer);
            while(true){
//                synchronized (consumer){
//                    try{
//                        consumer.wait();
//                    }catch (InterruptedException ex){
//                        ex.printStackTrace();


//                    }
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(conn != null){
                    conn.close();
                }
            }catch (IOException ioe){

            }

        }
    }

}
