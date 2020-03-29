package net.multi.terminal.bff.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.google.common.base.Charsets;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;

/**
 * 缓存配置类
 */
@Configuration
public class CacheConfig {
    @Bean
    public RedisTemplate<String,Object> template(RedisConnectionFactory connectionFactory){
        RedisTemplate<String,Object> template = new RedisTemplate<String,Object>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer<Object> jsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        template.setConnectionFactory(connectionFactory);
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // valuevalue采用jackson序列化方式
        template.setValueSerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // hash的value采用jackson序列化方式
        template.setHashValueSerializer(jsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
