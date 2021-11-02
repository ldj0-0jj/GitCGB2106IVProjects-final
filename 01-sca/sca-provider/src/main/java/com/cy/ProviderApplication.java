package com.cy;

import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//JVM 类加载参数
//-XX:+TraceClassLoading
@Slf4j
@SpringBootApplication
public class ProviderApplication {
    // private  static final Logger log=LoggerFactory.getLogger(ProviderController.class);
    public static void main(String[] args) throws InterruptedException {
      SpringApplication.run(ProviderApplication.class,args);
    }
    @RestController
    public  class ProviderController{
        //@Value用于读取application.yml中的配置
        //要读取的配置需要写在${}这个表达式中
        //${}表达式中“:”后的内容为默认值
        @Value("${server.port:8080}")
        private String server;
        /**
         * 基于此方法实现一个字符串的回显
         * echo:回显的意思
         * rest:一种软件架构编码风格，可以基于这种风格定义url
         * 访问:http://localhost:8081/provider/echo/nacos
         */
        @GetMapping("/provider/echo/{msg}")
        public String doRestEcho1(
                @PathVariable("msg") String msg) throws InterruptedException {
            //System.out.println("===start====");
            //Thread.sleep(5000);
            //需求:通过配置中心中动态日
            // 志级别配置,控制日志信息的输出
            //常用日志级别: trace<debug<info<warn<error
            //很多系统的默认日志级别是info,调试程序时经常会用debug
            log.info("doRestEcho1 start {}",//{} 表示占位符
                    System.currentTimeMillis());
            return server+" say hello "+msg;
        }
    }
}
