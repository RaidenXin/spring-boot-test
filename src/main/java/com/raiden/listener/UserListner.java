package com.raiden.listener;


import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@RocketMQMessageListener(
        topic = "test_topic",
        consumerGroup = "test_my-consumer",
        consumeThreadMax = 2,
        selectorExpression = "*")
@Component
public class UserListner implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        System.out.println(Thread.currentThread().getName() + " onMessage: " + new String(messageExt.getBody()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
