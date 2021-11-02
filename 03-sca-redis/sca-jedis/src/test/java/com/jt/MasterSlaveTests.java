package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * 主从架构测试分析
 */
public class MasterSlaveTests {

    @Test //Master节点(支持读写)
    public void testWriteRead(){
        Jedis jedis=new Jedis("192.168.126.129", 6379);
        jedis.set("a1", "100");
        String a1 = jedis.get("a1");
        System.out.println(a1);
        jedis.close();
    }


    @Test //Slave节点(只允许读)
    public void testRead(){
        Jedis jedis=new Jedis("192.168.126.129", 6380);
        //jedis.set("a1", "100");这
        // 里不允许写
        String a1 = jedis.get("a1");
        System.out.println(a1);
        jedis.close();
    }
}
//可靠性