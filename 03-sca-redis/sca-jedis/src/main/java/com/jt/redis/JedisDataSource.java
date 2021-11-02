package com.jt.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 构建一个Jedis数据源，基于此数据源可以从一个Jedis池中
 * 获取连接(Jedis)，进而操作redis数据库。
 */
public class JedisDataSource {
    private static final JedisPool jedisPool;
    private static final String HOST="192.168.126.129";
    private static final int PORT=6379;
    static{
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(16);//最大连接数，默认为8
        config.setMaxIdle(60);
        jedisPool=new JedisPool(config,HOST,PORT);
    }
    /**
     * 获取一个连接对象
     * @return
     */
    public static Jedis getConnection(){
        return jedisPool.getResource();
    }
    /**
     * 提供一个外界可以获取池的方法，
     * 假如外界要关闭池，首先要获取此池对象。
     * @return
     */
    public static JedisPool getJedisPool() {
        return jedisPool;
    }
}
