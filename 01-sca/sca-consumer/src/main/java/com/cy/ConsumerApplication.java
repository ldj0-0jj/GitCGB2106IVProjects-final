package com.cy;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.cy.sentinel.ServiceBlockExceptionHandler;
import com.cy.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 服务消费方对象的启动类
 * 业务描述：
 * 当客户端(浏览器，手机app)向服务消费方发起请求时，
 * 服务消费方调用服务提供方的api，进而获取服务提供方
 * 的数据。
 * 例如：
 * 我们访问一个订单模块数据(例如我的订单)，订单模块中
 * 还要呈现商品信息。
 */
@EnableFeignClients
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
    /**
     * 构建RestTemplate对象，并将此对象交给spring管理，
     * 后续我们会通过此对象进行远程服务调用
     * @return
     */
    @Bean
    //@Bean("rt")
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    @LoadBalanced
    public RestTemplate loadBalancerRestTemplate(){
        return new RestTemplate();
    }


    @RestController
    public class ConsumerController{
        /**
         * 负载均衡客户端对象
         */
      @Autowired
      private LoadBalancerClient loadBalancerClient;

      @Autowired
      private RestTemplate restTemplate;

      @Autowired
      private RestTemplate loadBalancerRestTemplate;

      @Value("${spring.application.name}")
      private String appName;

      @Autowired
      private ConsumerService consumerService;


      //http://ip:port/consumer/findById?id=10
      @GetMapping("/consumer/findById")
      @SentinelResource(value="res")
      public String doFindById(Integer id){
          return " Resource id is "+id;
      }
      //构建一个线程安全并且可实现自增自减操作的整数对象
      private AtomicLong aLong=new AtomicLong(1);
      //http://ip:port/consumer/doRestEcho1
      @GetMapping("/consumer/doRestEcho1")
      public String doRestEcho1() throws InterruptedException {
          long num=aLong.getAndIncrement();
          if(num%2==0){
              //系统设计时会认为响应设计超过200毫秒为慢调用
              Thread.sleep(200);//模拟耗时操作
              //throw new RuntimeException();
          }
        //直接通过业务方法访问相关资源(现在不需要关心什么返回值)
        //consumerService.doGetResource();
        //System.out.println("==doRestEcho1()==");
        //调用服务提供方API(http://ip:port/path)
        //1.定义要调用的API
        String url=
        "http://localhost:8081/provider/echo/"+appName;
        //2.谁去访问这个API? restTemplate;
        return restTemplate
                .getForObject(url,
                        String.class);
      }
        /**
         * 负载均衡方式调用
         * @return
         */
        @GetMapping("/consumer/doRestEcho2")
        public String doRestEcho2 (){
            consumerService.doGetResource();
            //1.从注册中心获取服务实例
            ServiceInstance instance =
                    loadBalancerClient
                    .choose("sca-provider");
            //2.基于RestTemplate进行服务实例调用
            String ip=instance.getHost();//ip
            int port=instance.getPort();//port
            //String url= "http://"+ip+":"+port+"/provider/echo/"+appName;
            String url=String.format(
                    "http://%s:%s/provider/echo/%s",
                     ip,port,appName);
            return restTemplate.getForObject(
                    url, String.class);
        }
        @GetMapping("/consumer/doRestEcho3")
        public String doRestEcho3(){
            //定义url
            String serviceId="sca-provider";//服务名
            String url=
            String.format("http://%s/provider/echo/%s",serviceId,appName);
            //服务调用
            return loadBalancerRestTemplate.getForObject(
                    url, String.class);
        }
    }
}

//browser->provider
//browser->consumer
//browser-(url)->consumer-(url)->provider
