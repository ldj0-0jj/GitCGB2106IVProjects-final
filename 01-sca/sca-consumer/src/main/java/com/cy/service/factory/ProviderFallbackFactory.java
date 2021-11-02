package com.cy.service.factory;

import com.cy.service.RemoteProviderService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Slf4j 此注解由lombok提供,描述类时会自动在类中创建一个org.slf4j.Logger对象
@Component
public class ProviderFallbackFactory
        implements FallbackFactory<RemoteProviderService> {

    // org.slf4j.Logger是Java中的日志规范,定义了一组接口
    // org.slf4j.Logger这个接口的实现有log4j,logback
    private static Logger log=
          LoggerFactory.getLogger(ProviderFallbackFactory.class);
    @Override
    public RemoteProviderService create(Throwable throwable) {
         log.error("用户服务调用失败:{}", throwable.getMessage());
//       return new RemoteProviderService() {
//            @Override
//            public String echoMessage(String msg) {
//                //...给运维人员发消息..
//                return "服务忙,稍等片刻再访问";
//            }
//      };
        //如上写法的简化形式如下:借助了jdk8中lambda表达式

//        return (msg)-> {//lambda (jdk8中一个表达式)
//                //...给运维人员发消息..
//                return "服务忙,稍等片刻再访问";
//        };



          return msg->"服务忙,稍等片刻再访问";
    }
}

