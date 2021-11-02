package com.cy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RefreshScope 注解用于告诉spring,一般配置中心数据发生变化,
//自动重新创建它描述的类的实例,
@Slf4j
@RefreshScope
@RestController
public class ProviderLogController {
    public ProviderLogController(){
        System.out.println("ProviderController()");
    }
    //此属性会在对象构建时初始化
    //服务器启动时会将所有配置信息(配置文件或配置中心)读取到Environment对象
    //@Value用于告诉spring从Environment对象中读取配置信息
    //将读取到配置内容赋值给对象的属性
    @Value("${logging.level.com.cy:debug}")
    private String logLevel;

    @GetMapping("/provider/doGetLogLevel")
    public String doGetLogLevel(){
        //日志的输出会随着配置中心日志级别的更新进行调整
        log.trace("==log.trace==");//跟踪
        log.debug("==log.debug==");//调试
        log.info("==log.info==");//常规信息
        log.warn("==log.warn==");//警告
        log.error("==log.error==");//错误信息
        return "log level is "+logLevel;
    }

}
