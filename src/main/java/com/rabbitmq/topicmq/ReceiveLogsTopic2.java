package com.rabbitmq.topicmq;

import com.rabbitmq.client.*;

import java.io.UnsupportedEncodingException;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/19
 **/
public class ReceiveLogsTopic2 {
        private static final String EXCHANGE_NAME = "topic_logs";

        public static void main(String[] argv) throws Exception{
            new ReceiveLogsTopic2().test();
        }

        public void test()throws Exception{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
//      声明一个匹配模式的交换器
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            String queueName = channel.queueDeclare().getQueue();
            // 路由关键字
            String[] routingKeys = new String[]{"*.*.rabbit", "lazy.#"};
//      绑定路由关键字
            for (String bindingKey : routingKeys) {
                channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
                System.out.println("ReceiveLogsTopic2 exchange:"+EXCHANGE_NAME+", queue:"+queueName+", BindRoutingKey:" + bindingKey);
            }

            System.out.println("ReceiveLogsTopic2 Waiting for messages");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                    String message = new String(body, "UTF-8");
                    System.out.println("ReceiveLogsTopic2 Received '" + envelope.getRoutingKey() + "':'" + message + "'");
                }
            };
            channel.basicConsume(queueName, true, consumer);
    }
}
