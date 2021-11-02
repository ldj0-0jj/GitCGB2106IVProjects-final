package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * Redis中的事务测试
 * 什么事务? 事务一个逻辑工作单元,是一个业务,一般要求
 * 这个事务中的所有操作要么都执行,要么都不执行.
 * 为什么要使用事务(使用事务的目的是什么)?保证数据的正确
 * 事务基于什么特性保证数据的正确? ACID
 * redis 中的事务指令:
 * 1)multi (手动开启事务)
 * 2)discard(手动取消事务)
 * 3)exec (手动提交事务)
 */
public class JedisTransactionTests {
    @Test
    public void testTransaction(){
        //1.创建redis链接对象
        Jedis jedis=new Jedis("192.168.126.129", 6379);
        //2.定义转账业务初始数据
        jedis.set("tony", "1000");
        jedis.set("jack", "300");
        //3.开始事务
        Transaction multi = jedis.multi();
        //4.执行转账业务(tony转100给jack)
        try {
            multi.decrBy("tony", 100);
            multi.incrBy("jack", 100);
            //int num = 100 / 0;//模拟异常
            //5.提交事务
            multi.exec();
            System.out.println("transaction ok");
        }catch (Exception e){
            multi.discard();
           // e.printStackTrace();
             throw new RuntimeException(e);
        }finally{
            //6.释放资源
            jedis.close();
        }
    }
}
