package com.rabbitmq.delayqueue;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wgp on 2018/6/27.
 */
public class Recv {

    private final  String QUEUE_NAME = "queue_name";
    private final  String EXCHANGE_NAME = "header_exchange";

    private final String ROUTE_KEY = "routekey";

    private final  String EXCHANGE_NAME2 = "exchange_direct";


    public void recvAtoB()throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);
        //设置队列的过期时间为30 秒，消息过期转发给指定转发器，匹配的routingkey  可以不指定
        Map<String,Object> args = new HashMap<String, Object>();
        args.put("x-expires", 30000);//队列过期时间
        args.put("x-message-ttl", 12000);//队列上消息过期时间
        args.put("x-dead-letter-exchange", EXCHANGE_NAME2);//过期消息转向路由
        args.put("x-dead-letter-routing-key", ROUTE_KEY);//过期消息转向路由相匹配routingkey

        //队里名  是否持久化  私有化    自动删除  参数
        String queueName = channel.queueDeclare(QUEUE_NAME,true,false,false,args).getQueue();

        Map<String,Object> headers = new HashMap<String ,Object>();
        headers.put("x-match", "all");//all any(只要有一个键值对匹配即可)
        headers.put("key", "123456");

        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"",headers);
        System.out.println("Received ...");
        Consumer  consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = SerializationUtils.deserialize(body);
                System.out.println(envelope.getRoutingKey() + ":Received：" + message + "done");
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        //设置  手动应答机制  ， 默认是开启自动应答的，
        channel.basicConsume(queueName,false,consumer);

    }

    public static void main(String[] args) throws Exception{
        new Recv().recvAtoB();
    }
}
