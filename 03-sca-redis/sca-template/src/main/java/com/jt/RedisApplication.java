package com.jt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.jt.blog.domain.Tag;
import jdk.nashorn.internal.runtime.PropertyAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;
import java.util.List;

@EnableCaching //启动AOP方式的缓存配置
@SpringBootApplication
public class RedisApplication{
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class,args);
    }
}
//序列化：
//狭义层面：将对象转换为字节（I/O）
//广义层面：将对象转换为指定格式字符串(例如json)
//封装：
//狭义层面：属性私有化，方法能公开则公开
//广义层面：一个系统有哪些服务(子系统)，一个服务由哪些模块，一个模块有哪些对象，一个对象有哪些属性