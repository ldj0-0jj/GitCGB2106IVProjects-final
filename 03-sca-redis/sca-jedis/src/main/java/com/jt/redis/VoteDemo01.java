package com.jt.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 基于某个活动的投票系统的简易设计
 * 需求：
 * 1)基于用户id对活动进行投票
 * 2)一个用户id只能参与一次投票
 * 3)可以查询投票总数以及参数投票的用户id
 * 设计：
 * 1)数据存储结构：set结构(不允许重复)
 */
public class VoteDemo01 {
    static final String IP="192.168.126.129";
    static final int PORT=6379;
    /**
     * 检查用户是否参与过此活动的投票
     * @param activityId
     * @param userId
     */
    public static Boolean isVote(String activityId,String userId){
         //1.创建jedis对象
        Jedis jedis=new Jedis(IP, PORT);
         //2.检查是否投票
        Boolean flag = jedis.sismember(activityId, userId);
        //3.释放资源
        jedis.close();
         //4.返回结果
        return flag;
    }
    /**
     * 执行投票操作
     * @param activityId
     * @param userId
     */
    public static void doVote(String activityId,String userId){
        //1.创建Jedis对象
        Jedis jedis=new Jedis(IP, PORT);
        //2.执行投票
        jedis.sadd(activityId, userId);
        //3.释放资源
        jedis.close();
    }
    /**
     * 查看活动的总投票数量
     * @param activityId
     */
    public static Long  doGetVotes(String activityId){
        //1.创建Jedis对象
        Jedis jedis=new Jedis(IP, PORT);
        //2.查看活动的投票数
        Long count = jedis.scard(activityId);
        //3.释放资源
        jedis.close();
        //4.返回投票数量
        return count;
    }
    /**
     * 获取参与投票当前活动的用户
     * @param activityId
     */
    public static Set<String> doGetUsers(String activityId){
        //1.创建Jedis对象
        Jedis jedis=new Jedis(IP, PORT);
        //2.查看活动的投票数
        Set<String> smembers = jedis.smembers(activityId);
        //3.释放资源
        jedis.close();
        //4.返回投票数量
        return smembers;
    }

    public static void main(String[] args) {
        //1.定义活动id,用户id
        String activityId="10001";
        String user1="301";
        String user2="302";
        //2.进行投票检查
        Boolean flag=isVote(activityId, user1);
        System.out.println("flag="+flag);
        //3.进行投票
        doVote(activityId, user1);
        doVote(activityId, user2);
        //4.获取投票的总数
        Long aLong = doGetVotes(activityId);
        System.out.println(activityId +"活动的总投票数为 "+aLong);
        //5.获取参与投票的用户
        Set<String> users = doGetUsers(activityId);
        System.out.println("参与"+activityId+"活动的投票用户为"+users);
    }
}
