package com.jt.redis;

import redis.clients.jedis.Jedis;
/**分布式id生成策略*/
public class IdGenerator {
    /**
     * 编写一个方法，每次调用此方法
     * 外界都能够获取一个唯一的一个递增
     * 整数值。
     */
    public static Long getId(){
        Jedis jedis=new Jedis("192.168.126.129", 6379);
        //jedis.auth("123456");//密码
        //incr方法用于对指定key的值进行递增，假如key不存在则创建
        long id=jedis.incr("incrementId");
        jedis.close();
        return id;
    }
    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            new Thread(){
                @Override
                public void run() {
                    System.out.println(IdGenerator.getId());
                }
            }.start();
        }
    }
}
