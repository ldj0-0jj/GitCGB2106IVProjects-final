package com.jt.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.*;

@SpringBootTest
public class RedisTemplateTests {
    //这个对象在springboot工程的RedisAutoConfiguration类中已经做了配置
    //此对象在基于redis存取数据时默认采用的JDK的序列化方式
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testSetOper01(){
        //1.获取操作Set类型数据的操作对象
        SetOperations so = redisTemplate.opsForSet();
        //2.读写redis数据Set类型数据
        so.add("set1", "A","B","C","C");//不允许重复
        Set set1 = so.members("set1");
        System.out.println(set1);
        //.........
    }
    /**
     * 测试list类型数据的读写
     */
    @Test
    void testListOper01(){
        //1.获取操作list类型数据的操作对象
        ListOperations lo =
                redisTemplate.opsForList();
        //2.读写redis数据list类型数据
        //存数据
        lo.leftPush("lst1", "A");
        lo.leftPushAll("lst1","B","C","C","D");
        //取数据
        Object e = lo.leftPop("lst1");//取一个
        System.out.println(e);
        List lst1 = lo.range("lst1", 0, -1);
        System.out.println(lst1);
    }
    /**
     * 测试hash数据的存取
     */
    @Test
    void testHashOper01(){
        //1.获取操作hash类型数据的操作对象
        HashOperations ho = redisTemplate.opsForHash();
        //2.读写redis数据hash类型数据
        Map<String,Object> map=new HashMap<>();
        map.put("id", 100);
        map.put("title", "spring boot");
        //直接存储一个map
        ho.putAll("blog", map);//hash 存储，序列化
        //存储单个字段，值
        ho.put("blog","content", "spring boot redis");
        //取blog对象中id属性的值
        Object o = ho.get("blog", "id");
        System.out.println("id="+o);
        //获取整个blog对象所有属性和值
        Map blog = ho.entries("blog");//反序列化
        System.out.println(blog);
        //......
    }

    /**
     * 测试字符串数据的存取
     */
    @Test
    void testStringOper01(){
        //自己指定key/value序列化方式
        //redisTemplate.setKeySerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations vo = redisTemplate.opsForValue();
        //key和value默认会采用JDK的序列化方式进行存储
        vo.set("token", UUID.randomUUID().toString());
        Object token = vo.get("token");
        System.out.println(token);
    }

    @Test
    void testGetConnection(){
        RedisConnection connection =
                redisTemplate.getConnectionFactory()
                        .getConnection();
        String ping = connection.ping();
        System.out.println(ping);
    }

}
