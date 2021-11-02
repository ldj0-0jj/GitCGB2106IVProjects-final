package com.jt.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * 基于redis实现一个简单的多线程抢票操作
 * 这个操作中重点演示一下乐观锁的应用.
 * 乐观锁:允许多个线程同时对一条记录进行修改,但只能有一个线程修改成功
 */
public class SecondKillDemo01 {
    //定义抢票逻辑
    static void secondKillTicket(){
       //1.建立redis链接
       Jedis jedis=new Jedis("192.168.126.129", 6379);
       //2.监控redis中指定key
       String ticket = jedis.get("ticket");
       if(ticket==null||Integer.valueOf(ticket)==0)
           throw new RuntimeException("余票不足");
       jedis.watch("ticket","money");
       //3.开启事务并执行业务
           Transaction multi = jedis.multi();
       try {
           multi.decr("ticket");
           multi.incrBy("money", 100);
           //4.提交事务
           multi.exec();
           System.out.println("transaction ok");
       }catch (Exception e){
           multi.discard();
       }finally {
           //5.取消监控
           jedis.unwatch();
           //6.释放资源
           jedis.close();
       }
    }
    public static void main(String[] args) throws InterruptedException {
        //1.定义redis初始化数据
         Jedis jedis=
         new Jedis("192.168.126.129", 6379);
         jedis.set("ticket", "1");
         jedis.set("money", "0");
         jedis.close();
        //2.创建多个线程,在线程中执行抢票操作
         Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                //执行抢票
                secondKillTicket();
            }
         });
         Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                //执行抢票
                secondKillTicket();
            }
         });
        //3.启动多个线程
         t1.start();
         t2.start();
    }
}
