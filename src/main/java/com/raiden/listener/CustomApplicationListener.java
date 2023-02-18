package com.raiden.listener;

import com.raiden.concurrent.CustomThreadPoolExecutor;
import com.raiden.concurrent.listener.ThreadPoolListener;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 13:40 2021/2/15
 * @Modified By:
 */
//@Component
@Slf4j
public class CustomApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final String CONSUME_EXECUTOR = "consumeExecutor";
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, DefaultRocketMQListenerContainer> beans = applicationContext.getBeansOfType(DefaultRocketMQListenerContainer.class);
        if (beans != null){
            beans.entrySet().stream().forEach(d -> {
                DefaultRocketMQListenerContainer value = d.getValue();
                DefaultMQPushConsumer consumer = value.getConsumer();
                if (consumer != null){
                    DefaultMQPushConsumerImpl defaultMQPushConsumerImpl = consumer.getDefaultMQPushConsumerImpl();
                    if (defaultMQPushConsumerImpl != null){
                        ConsumeMessageService consumeMessageService = defaultMQPushConsumerImpl.getConsumeMessageService();
                        setThreadPoolExecutor(consumeMessageService, getListeners(applicationContext));
                    }
                }
            });
        }
    }

    private void setThreadPoolExecutor(ConsumeMessageService consumeMessageService,List<ThreadPoolListener> listeners){
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
                Field consumeExecutor = clazz.getDeclaredField(CONSUME_EXECUTOR);
                consumeExecutor.setAccessible(true);
                Object o = consumeExecutor.get(consumeMessageService);
                if (o instanceof ThreadPoolExecutor){
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) o;
                    ThreadPoolListener listener = ThreadPoolListener.getDefaultInstance(listeners);
                    consumeExecutor.set(consumeMessageService, new CustomThreadPoolExecutor(threadPoolExecutor, listener, TimeUnit.MILLISECONDS));
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private List<ThreadPoolListener> getListeners(ApplicationContext applicationContext){
        if (applicationContext == null){
            return new ArrayList<>();
        }
        Map<String, ThreadPoolListener> beans = applicationContext.getBeansOfType(ThreadPoolListener.class);
        if (beans == null){
            return new ArrayList<>();
        }
        return beans.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
}
