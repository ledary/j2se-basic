package com.rabbitmq.delayqueue;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 实际场景用到延迟消息发送，比如 例如支付场景，准时支付、超过未支付将执行不同的方案，其中超时未支付可以看做一个延时消息
 * 给消费设置过期时间，在消息队列上为过期消息制定转发器。
 * 消息过期后会转发到指定转发器匹配的队列上。变向实现延迟队列。
 * Created by wgp on 2018/6/27.
 */
public class Send {
    private final  String EXCHANGE_NAME = "header_exchange";
    public void sendAtoB(Serializable obj) throws Exception {
        //工厂返回和rabbitMQd 连接对象
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();

        //建立连接通道
        Channel channel = conn.createChannel();
        //声明交换机   交换机名字  类型
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);
        Map<String, Object> headers = new HashMap<>();
        headers.put("key", "123456");
        headers.put("token", "654321");
        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
        properties.headers(headers);
        //持久化
        properties.deliveryMode(2);

        //指定消息过期时间为12秒  队列上也可以指定消息的过期时间
        properties.expiration("12000");

        channel.basicPublish(EXCHANGE_NAME,"",
                properties.build(), SerializationUtils.serialize(obj));
        System.out.println("' Send '" + obj + "'");
        channel.close();
        conn.close();
    }

    public static void main(String[] args) throws Exception{
        new Send().sendAtoB("Hello World");
        System.in.read();
    }
}