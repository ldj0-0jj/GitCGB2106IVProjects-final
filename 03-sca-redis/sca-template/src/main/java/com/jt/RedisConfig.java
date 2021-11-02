package com.jt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisConfig extends CachingConfigurerSupport{
    /**
     * 定义缓存key生成器，不定义也可以使用默认的。
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target,
                                   Method method,
                                   Object... params) {
                StringBuilder sb=new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append("::");
                sb.append(method.getName());
                for(Object param:params){//方法没有参数就没有这个循环了
                    sb.append(param);
                }
                return sb.toString();
            }
        };
    }

    /**
     * 自定义Cache管理器对象，不定义也可以，有默认的，但假如希望基于AOP
     * 方式实现Redis的操作时，按照指定的序列化方式进行序列化，
     * 可以对CacheManager进行自定义。
     * @param connectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory connectionFactory){
        RedisCacheConfiguration config=
                RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(60*60))//设置key的有效期
        //配置key的序列化
        .serializeKeysWith(RedisSerializationContext
                .SerializationPair.fromSerializer(
                        new StringRedisSerializer()))
        //配置值的序列化
        .serializeValuesWith(RedisSerializationContext
                .SerializationPair.fromSerializer(
                        jsonRedisSerializer()))
        .disableCachingNullValues();//不存储null值
        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(config)//修改默认配置
                .transactionAware()//启动默认事务
                .build();
    }
    //自定义RedisTemplate中的序列化方式
    //代码定制参考RedisAutoConfiguration类
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        System.out.println("===redisTemplate===");
        RedisTemplate<Object,Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //定义redisTemplate对象的序列化方式
        //1.定义key的序列化方式
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        //2.定义Value的序列化方式
        template.setValueSerializer(jsonRedisSerializer());
        template.setHashValueSerializer(jsonRedisSerializer());
        //3.redisTemplate默认特性设置(除了序列化，其它原有特性不丢)
        template.afterPropertiesSet();
        return template;
    }
    @Bean
    public Jackson2JsonRedisSerializer jsonRedisSerializer(){
        Jackson2JsonRedisSerializer jsonRedisSerializer=
                new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setVisibility(
                PropertyAccessor.GETTER,
                JsonAutoDetect.Visibility.ANY);
        //激活默认类型(存储json时会添加类型信息到json串中)
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        jsonRedisSerializer.setObjectMapper(objectMapper);
        return jsonRedisSerializer;
    }
}
