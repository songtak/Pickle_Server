package com.pickle.web.chatbot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class DBConfig {
    @Bean
    public RedisConnectionFactory fac() {
        LettuceConnectionFactory fac = new LettuceConnectionFactory();
        return fac;
    }
    @Bean
    public RedisTemplate<String, Object> tem() {
        RedisTemplate<String, Object> tem = new RedisTemplate<>();
        tem.setConnectionFactory(fac());
        tem.setKeySerializer(new StringRedisSerializer());
        tem.setValueSerializer(new StringRedisSerializer());
        tem.setHashKeySerializer(new StringRedisSerializer());
        tem.setHashValueSerializer(new StringRedisSerializer());
        return tem;
    }
}
