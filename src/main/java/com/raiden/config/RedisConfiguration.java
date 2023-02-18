package com.raiden.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 12:19 2022/1/15
 * @Modified By:
 */
@Configuration
public class RedisConfiguration {

    /**
     * 默认的 {@link org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration}
     *  会注入2个bean对象,
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        /**
         *  TODO 自己选择一个 序列化协议
         * 需要注意的一点是，RedisTemplate 现在demo给出的序列化协议使用的是 GenericJackson2JsonRedisSerializer 。
         * 如果你需要多个项目公用缓存，A项目写缓存，B项目读缓存，如果是这种情况，请一定要保证 2边的序列化协议要一致。
         * 不要出现了 A项目使用了 jdk序列化 put，B项目使用的jackson的序列化get，这样子 肯定会报错的。
         * 所以对于当前项目用什么序列化协议一定要考虑到有没有多个项目缓存共享的情况。不要选错了导致后期很难切序列化协议
         */
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        // key 使用String 序列化(只能使用String 作为redis key)
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        return template;
    }
}
