package com.jt.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 购物车系统简易实现
 * 需求：
 * 1)向购物车添加商品
 * 2)修改购物车商品数量
 * 3)查看购物车商品
 * 4)清空购车商品信息
 * 存储设计：
 * 1)mysql (优势是持久性，读写速度会差一些)
 * 2)redis (优势是性能，持久性相对MySQL会差一下，
 * 假如性能要求高于数据的存储安全，可以存储到redis)
 */
public class CartDemo01 {
    static final String IP="192.168.126.129";
    static final int PORT=6379;

    /**
     * 向购物车添加商品
     * @param userId
     * @param productId
     * @param num
     */
    public static void doAddCart(String userId,
                                 String productId,
                                 int num){
        //1.创建jedis对象
        Jedis jedis=new Jedis(IP,PORT);
        //2.基于hash结构存储商品信息
        //jedis.hset()
        jedis.hincrBy("cart:"+userId, productId, num);
        //3.释放资源
        jedis.close();
    }

    /**
     * 查看购物车商品信息
     * @param userId
     */
    public static  Map<String, String> doViewCart(
                          String userId){
        //1.创建jedis对象
        Jedis jedis=new Jedis(IP,PORT);
        //2.查看购物车信息
        Map<String, String> cart =
                jedis.hgetAll("cart:" + userId);
        //3.释放资源
        jedis.close();
        //4.返回购物车商品信息
        return cart;
    }

    /**
     * 删除购车某个商品
     * @param userId
     * @param productId
     */
    public static void doClearCart(String userId,
                                 String... productId){
        //1.创建jedis对象
        Jedis jedis=new Jedis(IP,PORT);
        //2.清除指定商品
        jedis.hdel("cart:"+userId, productId);
        //3.释放资源
        jedis.close();
    }

    public static void main(String[] args){
        String userId="1001";
        //1.购买商品时，将商品信息添加到购物车
        doAddCart(userId,"201",1);
        doAddCart(userId,"202",1);
        doAddCart(userId,"203",1);
        //2.修改购车商品的数量
        doAddCart(userId,"202",1);
        doAddCart(userId,"203",2);
        //3.查看购物车商品
        Map<String, String> cart =
                doViewCart(userId);
        System.out.println(cart);
        //4.清空购物车
        doClearCart(userId, "201","202","203");
        cart = doViewCart(userId);
        System.out.println(cart);
    }
}
