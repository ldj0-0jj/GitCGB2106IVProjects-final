package com.jt;

import com.google.gson.Gson;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class JedisTests {

    @Test
    public void testSetOper01(){
        //1.建立链接
        Jedis jedis=new Jedis("192.168.126.129",6379);
        //2.存储数据(Set集合没有顺序，不允许重复)
        jedis.sadd("set1", "A","A","B","C","D");
        //3.修改数据
        //4.删除数据
        //jedis.spop("set1");
        jedis.srem("set1", "B");
        //5.查询数据
        Set<String> set1 = jedis.smembers("set1");
        System.out.println(set1);
        Long cards = jedis.scard("set1");
        System.out.println(cards);
        //5.释放资源
        jedis.close();
    }

    /**
     * 阻塞式队列应用
     */
    @Test
    public void testListOper02(){
        //1.建立链接
        Jedis jedis=new Jedis("192.168.126.129",6379);
        //2.存储数据(list集合有顺序，允许重复)
        jedis.lpush("list2", "A","B","C");
        //3.基于阻塞方式取数据
        jedis.brpop(50, "list2");
        jedis.brpop(50, "list2");
        List<String> lst1 = jedis.brpop(50, "list2");
        System.out.println(lst1);//输出的内容为key和pop出的值
        jedis.brpop(50, "list2");
        //4.释放资源
        jedis.close();
    }
    /**
     * 测试redis中list类型的应用
     */
    @Test
    public void testListOper01(){
        //1.建立链接
        Jedis jedis=new Jedis("192.168.126.129",6379);
        //2.存储数据(list集合有顺序，允许重复)
        jedis.lpush("lst1", "A","B","C","C");
        //3.修改数据
        //3.1首先获取要修改的元素的下标
        Long index = jedis.lpos("lst1", "A");
        //3.2基于下标，修改元素内容
        jedis.lset("lst1", index, "D");
        //4.删除数据
        System.out.println(jedis.llen("lst1"));
        jedis.rpop("lst1", 1);
        System.out.println(jedis.llen("lst1"));
        //5.查询数据
        List<String> lst1 = jedis.lrange("lst1", 0, -1);
        System.out.println(lst1);
        //6.释放资源
        jedis.close();
    }

    /**直接存取map对象*/
    @Test
    public void testHashOper02(){
        //1.建立链接
        Jedis jedis=new Jedis("192.168.126.129",6379);
        //2.存储一个map对象
        Map<String,String> map=new HashMap<>();
        map.put("x", "100");
        map.put("y", "200");
        jedis.hset("point", map);
        //3.读取一个map对象
        Map<String, String> point = jedis.hgetAll("point");
        System.out.println(point);
        //4.释放资源
        jedis.close();
    }

    /**
     * hash类型测试
     */
    @Test
    public void testHashOper01(){
        //1.建立链接
        Jedis jedis=new Jedis("192.168.126.129",6379);
        //2.向redis中存储数据
        jedis.hset("user", "id", "100");
        jedis.hset("user", "name", "jack");
        jedis.hset("user", "mobile", "1111111");
        //3.更新redis中指定数据
        jedis.hset("user", "id", "101");
        //4.删除redis中指定数据
        jedis.hdel("user", "mobile");
        //5.查看redis中的数据
        String id = jedis.hget("user", "id");
        System.out.println("user.id="+id);
        Map<String, String> user = jedis.hgetAll("user");
        System.out.println("user="+user);
        //6.释放资源
        jedis.close();
    }

    /**
     * 将对象转换为字符串，然后以字符串类型的方式存储到redis,
     * 这种的的最大缺陷：修改对象数据不方便
     */
    @Test
    public void testStringJsonOper(){
        //1.建立连接
        Jedis jedis=new Jedis("192.168.126.129",6379);
        //2.将对象转换为json字符串
        Map<String,Object> map=new HashMap<>();
        map.put("id", "101");
        map.put("name","tony");
        Gson gson=new Gson();//Google公司提供
        String jsonStr=gson.toJson(map);//将map转换为json串
        System.out.println("jsonStr="+jsonStr);
        //3.将json字符串存储到redis
        jedis.set("member", jsonStr);
        //4.取出member对象的值
        String member = jedis.get("member");
        System.out.println("redis.member="+member);
        //5.将json字符串转换为Map对象
        map=gson.fromJson(member, Map.class);
        System.out.println(map);
        //6.释放资源
        jedis.close();
    }
    /**
     * 基于junit+jedis对redis中的string类型进行测试
     */
    @Test
    public void testStringOper() throws InterruptedException {
        //1.建立链接
        Jedis jedis=new Jedis("192.168.126.129",6379);
        //2.向redis中存储数据
        jedis.set("id", "1");
        jedis.set("name", "Tony");
        jedis.set("token", UUID.randomUUID().toString());
        jedis.expire("token", 2);
        //3.更新redis中指定数据
        jedis.set("id", "100");
        jedis.incr("id");
        jedis.incrBy("id", 2);
        jedis.decr("id");
        //4.删除redis中指定数据
        jedis.del("name");
        //5.查看redis中的数据
        String id = jedis.get("id");
        String token=jedis.get("token");
        Long tokenStrLen=jedis.strlen("token");
        String name=jedis.get("name");

        System.out.println("id="+id);
        System.out.println("name="+name);
        System.out.println("token="+token);
        System.out.println("token.length="+tokenStrLen);
        //Thread.sleep(2000);
        TimeUnit.SECONDS.sleep(2);
        token=jedis.get("token");
        System.out.println("token="+token);
        //6.释放资源
        jedis.close();
    }
    @Test
    public void testGetConnection(){ //JDK8
        Jedis jedis=new Jedis("192.168.126.129",6379);
        //jedis.auth("123456") 假如设置了密码还要执行这个语句
        String ping = jedis.ping();
        System.out.println(ping);//PONG
    }

}
