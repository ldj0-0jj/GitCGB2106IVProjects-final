package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RDBTests {
    @Test
    public void testSave(){
        Jedis jedis=new Jedis("192.168.126.129", 6379);
        jedis.set("id", "100");
        //jedis.save();
        jedis.bgsave();
        jedis.set("price", "200");
        jedis.close();
    }
}
