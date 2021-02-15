package com.raiden.listener;

import com.raiden.concurrent.CustomThreadPoolExecutor;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.impl.consumer.ConsumeMessageConcurrentlyService;
import org.apache.rocketmq.client.impl.consumer.ConsumeMessageOrderlyService;
import org.apache.rocketmq.client.impl.consumer.ConsumeMessageService;
import org.apache.rocketmq.client.impl.consumer.DefaultMQPushConsumerImpl;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 13:40 2021/2/15
 * @Modified By:
 */
@Component
public class CustomApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, DefaultRocketMQListenerContainer> beansOfType = applicationContext.getBeansOfType(DefaultRocketMQListenerContainer.class);
        if (beansOfType != null){
            beansOfType.entrySet().stream().forEach(d -> {
                DefaultRocketMQListenerContainer value = d.getValue();
                DefaultMQPushConsumer consumer = value.getConsumer();
                if (consumer != null){
                    DefaultMQPushConsumerImpl defaultMQPushConsumerImpl = consumer.getDefaultMQPushConsumerImpl();
                    if (defaultMQPushConsumerImpl != null){
                        ConsumeMessageService consumeMessageService = defaultMQPushConsumerImpl.getConsumeMessageService();
                        setThreadPoolExecutor(consumeMessageService);
                    }
                }
            });
        }
    }

    private void setThreadPoolExecutor(ConsumeMessageService consumeMessageService){
        if (consumeMessageService == null){
            return;
        }
        synchronized (consumeMessageService){
            Class clazz;
            if (consumeMessageService instanceof ConsumeMessageOrderlyService){
                clazz = ConsumeMessageOrderlyService.class;
            }else if (consumeMessageService instanceof ConsumeMessageConcurrentlyService){
                clazz = ConsumeMessageConcurrentlyService.class;
            }else {
                return;
            }
            try {
                Field consumeExecutor = clazz.getDeclaredField("consumeExecutor");
                consumeExecutor.setAccessible(true);
                Object o = consumeExecutor.get(consumeMessageService);
                if (o instanceof ThreadPoolExecutor){
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) o;
                    consumeExecutor.set(consumeMessageService, new CustomThreadPoolExecutor(threadPoolExecutor, TimeUnit.MILLISECONDS));
                }
            } catch (Exception e) {
            }
        }
    }
}
