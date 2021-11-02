package com.cy;

import com.example.DefaultCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

//将一个bean交给spring管理时，假如这个bean不在spring所在包或者子包中，
//可以通过import方法将类导入到配置类中
//@Import(DefaultCache.class)
@SpringBootApplication
public class MainApplication {
//  @Bean
//  public DefaultCache defaultCache(){
//       System.out.println("defaultCache()");
//        return new DefaultCache();
//  }

   @Bean
   //这个注解表示没有指定的bean对象时，才会执行它描述的方法创建对象
   @ConditionalOnMissingBean(DefaultCache.class)
   public DefaultCache defaultCache02(){
        System.out.println("defaultCache02()");
        return new DefaultCache();
   }

   public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
   }
}
