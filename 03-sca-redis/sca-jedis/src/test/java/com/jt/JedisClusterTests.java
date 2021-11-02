package com.jt;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

public class JedisClusterTests {

    @Test
    public void testJedisCluster(){
       //1.创建Jedis集群操作对象
        Set<HostAndPort> nodes=new HashSet<>();
        nodes.add(new HostAndPort("192.168.126.129", 8010));
        nodes.add(new HostAndPort("192.168.126.129", 8011));
        nodes.add(new HostAndPort("192.168.126.129", 8012));
        nodes.add(new HostAndPort("192.168.126.129", 8013));
        nodes.add(new HostAndPort("192.168.126.129", 8014));
        nodes.add(new HostAndPort("192.168.126.129", 8015));
        JedisCluster jedisCluster = new JedisCluster(nodes);
       //2.执行数据读写操作
        jedisCluster.set("username", "tony");
        String username = jedisCluster.get("username");
        System.out.println(username);
        //3.释放资源
        jedisCluster.close();
    }
    @Test
    public void testJedisClusterPool(){
        //1.创建Jedis集群操作对象
        Set<HostAndPort> nodes=new HashSet<>();
        nodes.add(new HostAndPort("192.168.126.129", 8010));
        nodes.add(new HostAndPort("192.168.126.129", 8011));
        nodes.add(new HostAndPort("192.168.126.129", 8012));
        nodes.add(new HostAndPort("192.168.126.129", 8013));
        nodes.add(new HostAndPort("192.168.126.129", 8014));
        nodes.add(new HostAndPort("192.168.126.129", 8015));
        //定义链接池的配置
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMinIdle(60);

        JedisCluster jedisCluster =
                new JedisCluster(nodes,config);
        //2.执行数据读写操作
        jedisCluster.set("username", "tony");
        String username = jedisCluster.get("username");
        System.out.println(username);
        //3.释放资源
        jedisCluster.close();
    }
}//集群优势: 扩容,高可用(可靠性)

//Redis如何解决数据的可靠性问题的?
//1)持久化(rdb,aof)
//2)主从+哨兵,集群
