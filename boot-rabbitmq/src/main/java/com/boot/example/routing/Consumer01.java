package com.boot.example.routing;

import com.boot.example.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;

/**
 * @author TuoZhou
 */
public class Consumer01 {

    /**
     * 队列名称1
     */
    public static final String QUEUE_NAME1 = "routing-1";

    /**
     * 接收消息
     */
    public static void main(String[] args) throws Exception {
        // 获取信道
        Channel channel = RabbitMqUtils.getChannel();

        // 接收消息
        // 1、队列名称
        // 2、是否自动应答
        // 3、消息传递回调
        // 4、消费者取消消费回调
        channel.basicConsume(QUEUE_NAME1, false,
                (consumerTag, message) -> {
                    System.out.println("消息接收成功：" + "\n" +
                            "consumerTag：" + consumerTag + "\n" +
                            "message：" + new String(message.getBody()));

                    // 手动应答 basicAck basicNack basicReject
                    // 1、消息的标记
                    // 2、是否批量应答
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                },
                (consumerTag) -> {
                    System.out.println("消费者取消消费：" + "\n" +
                            "consumerTag：" + consumerTag);
                });
    }

}
