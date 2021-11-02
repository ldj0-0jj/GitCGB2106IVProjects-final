package com.jt.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.UUID;

/**
 * SSO(单点登录系统) 案例演示：
 * 1)访问资源(假如没有登录，要提示先登录,如何判定是否登录了)
 * 2)执行登录(登录成功，存储用户登录信息)
 * 3)访问资源(已登录)
 * 解决方案
 * 1)方案1:SpringSecurity+jwt+oauth2 (并发比较大)
 * 2)方案2:SpringSecurity+redis+oauth2 (中小型并发)
 *
 */
public class SSODemo02 {
    /**认证中心的登录设计*/
    static String doLogin(String username,String password){
     //1.执行用户身份校验
      if(!"jack".equals(username))//将来这个jack来自数据库
          throw new IllegalArgumentException("这个用户不存在");
     //2.用户存在并且密码正确，表示用户是系统的合法用户
      if(!"123456".equals(password))
          throw new IllegalArgumentException("密码不正确");
     //3.将合法用户信息存储到redis
        Jedis jedis=new Jedis("192.168.126.129", 6379);
        String token=UUID.randomUUID().toString();
        jedis.hset(token,"username",username);
        jedis.hset(token, "status", "1");
        //....
        jedis.expire(token, 2);
        jedis.close();
        return token;//token
    }
    /**获取资源服务中的资源*/
    static Object doGetResource(String token){
      //1.检查用户是否已经登录
       if(token==null||"".equals(token))
           throw new IllegalArgumentException("请先登录");
       Jedis jedis=new Jedis("192.168.126.129", 6379);
       Map<String,String> map=jedis.hgetAll(token);
       jedis.close();
      //2.假如没有登录，则提示先登录
        System.out.println("map="+map);
       if(map.isEmpty())
           throw new RuntimeException("登录超时或token无效，请重新登录");
      //3.已登录则可以访问资源
        System.out.println("继续访问资源");
        //.....
        return "the resource of user";
    }
    //假设这里的main方法为客户端
    public static void main(String[] args) throws InterruptedException {
        String token=null;
       //第一次访问资源
       // doGetResource(token);
       //执行登录操作(将来认证要在认证中心实现)
        token=doLogin("jack", "123456");
       //第二次访问资源
        Thread.sleep(2000);
        doGetResource(token);
    }
}
