package com.shashank.redis.config;

import com.shashank.redis.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.UUID;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<UUID, Product> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {

        Jackson2JsonRedisSerializer<Product> valueSerializer = new Jackson2JsonRedisSerializer<>(Product.class);
        RedisSerializationContext<UUID, Product> context = RedisSerializationContext
                .<UUID, Product>newSerializationContext(new GenericToStringSerializer<>(UUID.class))
                .value(valueSerializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}

