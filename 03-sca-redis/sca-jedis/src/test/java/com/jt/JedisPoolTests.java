package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 测试jedis连接池的一个基本应用
 */
public class JedisPoolTests {

    @Test
    public void testJedisPool(){
        //1.构建jedis连接池(JedisPool对象)
        //1.1定义连接池配置
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(16);//最大连接数，默认为8
        config.setMaxIdle(60);//最大空闲时间(连接后续不用了，超出一定空闲时间要释放)
        //config.setxxxx

        //1.2定义连接的url和端口
        String host="192.168.126.129";
        int port=6379;
        //1.3创建连接池
        JedisPool jedisPool=
                new JedisPool(config,host,port);
        //2.从池中获取连接(jedis对象)
        Jedis resource = jedisPool.getResource();
        //3.执行redis操作
        resource.set("pool", "JedisPool");
        String pool = resource.get("pool");
        System.out.println(pool);
        //4.释放资源(不是关，而是将连接还回池中)
        resource.close();
        //5.关闭池(一般服务停止时关)
        //jedisPool.close();
    }
}
