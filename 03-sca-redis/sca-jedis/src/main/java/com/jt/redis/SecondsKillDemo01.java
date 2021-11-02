package com.jt.redis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * 演示抢购活动中的秒杀队列
 * 数据逻辑结构：list
 * 算法：FIFO (公平特性)
 * 数据存储结构：redis中的list类型进行存储
 *
 * 在抢购活动中会执行这样的操作：
 * 1)生产者(购买商品的用户):创建请求并将请求存储到队列
 * 2)消费者(处理购买请求的底层对象):从队列取请求，然后处理请求
 */
public class SecondsKillDemo01 {
     /**
     * 入队操作
     */
     public static void enque(String request){
         Jedis jedis=new Jedis("192.168.126.129", 6379);
         jedis.lpush("queue-1",request);
         jedis.close();
     }
    /**
     * 出队操作
     */
     public static String deque(){
         Jedis jedis=new Jedis("192.168.126.129", 6379);
         //非阻塞式取数据
         //return jedis.rpop("queue-1");
         //阻塞式取数据
         List<String> list = jedis.brpop(60, "queue-1");
         jedis.close();
         return list!=null?list.get(1):null;//0为key
     }

    public static void main(String[] args) {
        //1.构建生产对象(购买商品的用户)
        Thread t1=new Thread(){
            @Override
            public void run() {
                int i=1;
                for(;;){
                    enque("request-"+i++);
                    try { Thread.sleep(1000);
                    }catch (Exception e){}
                }
            }
        };
        //2.构建消费者对象(处理请求的对象)
        Thread t2=new Thread(){
            @Override
            public void run() {
                for(;;){
                    String request=deque();
                    if(request==null)continue;
                    System.out.println("process "+request);
                }
            }
        };
        //3.开启抢购活动
        t1.start();
        t2.start();
    }
}
