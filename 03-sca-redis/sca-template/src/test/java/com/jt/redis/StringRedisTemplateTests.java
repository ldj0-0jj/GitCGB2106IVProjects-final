package com.jt.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class StringRedisTemplateTests {
    /**
     * 此对象为操作redis的一个客户端对象,这个对象
     * 对key/value采用了字符串的序列化(StringRedisSerializer)
     * 方式进行,redis数据的读写操作.
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testHashOper01(){
       //1.获取hash操作的对象
        HashOperations<String, Object, Object> vo =
                stringRedisTemplate.opsForHash();
       //2.读写redis数据
       //2.1存储一个对象
       vo.put("user", "id", "100");
       vo.put("user", "username", "tony");
       vo.put("user", "status", "1");
       //2.2获取一个对象
       //2.2.1获取对象某个属性值
       Object status =vo.get("user","status");
       System.out.println(status);
        //2.2.2获取对象某个key对应的所有值
       List<Object> user = vo.values("user");
       System.out.println(user);
    }
    @Test
    void testStringOper02() throws JsonProcessingException {
        //1.获取字符串操作对象(ValueOperations)
        ValueOperations<String, String> vo =
                stringRedisTemplate.opsForValue();
        //2.读写redis中的数据
        Map<String,String> map=new HashMap<>();
        map.put("id","100");
        map.put("title","StringRedisTemplate");
        //将map对象转换为json字符串写到redis数据库
        String jsonStr=//jackson (spring-boot-starter-web依赖中自带)
        new ObjectMapper().writeValueAsString(map);
        vo.set("blog", jsonStr);
        jsonStr=vo.get("blog");
        System.out.println(jsonStr);
        //将json字符串转换为map对象
        map=
        new ObjectMapper().readValue(jsonStr, Map.class);
        System.out.println(map);
    }
    @Test
    void testStringOper01(){
        //1.获取字符串操作对象(ValueOperations)
        ValueOperations<String, String> vo =
                stringRedisTemplate.opsForValue();
        //2.读写redis中的数据
        vo.set("x", "100");
        vo.increment("x");
        vo.set("y", "200", 1, TimeUnit.SECONDS);
        String x = vo.get("x");
        String y = vo.get("y");
        System.out.println("x="+x+",y="+y);
    }
    @Test
    void testGetConnection(){
        RedisConnection connection =
                stringRedisTemplate.getConnectionFactory()
                        .getConnection();
        String ping = connection.ping();
        System.out.println(ping);
    }

}
