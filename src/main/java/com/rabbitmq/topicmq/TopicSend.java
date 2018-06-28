package com.rabbitmq.topicmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/19
 **/
public class TopicSend {

    private static final String EXCHANGE_NAME = "topic_logs";


    public static void main(String[] args)throws Exception {
        new TopicSend().test();
    }
    public void test() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String[] routingKeys = new String[]{
                "quick.orange.rabbit",
                "lazy.orange.elephant",
                "quick.orange.fox",
                "lazy.brown.fox",
                "quick.brown.fox",
                "quick.orange.male.rabbit",
                "lazy.orange.male.rabbit"
        };
        for (String key : routingKeys) {
            String message = "From " + key + "message";
            channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes());
            System.out.println("TopicSend Sent '" + key + "':'" + message + "'");
        }
        channel.close();
        conn.close();
    }


}
