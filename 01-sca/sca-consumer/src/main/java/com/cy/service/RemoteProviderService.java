package com.cy.service;

import com.cy.service.factory.ProviderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 定义用于实现远程provider服务调用的service接口
 * 其中:
 * 1)@FeignClient 用于描述远程服务调用接口
 * 2)name="sca-provider" 为你要远程调用的服务名
 * 3)contextId为当前bean的名称,假如没有指定contextId
 * ,默认会采用@FeignClient注解中name属性指定的名字作为
 * bean的名字
 * 4)fallbackFactory用于定义服务调用超时等现象发生时,
 * 一种应对措施或处理机制.
 */
@FeignClient(name="sca-provider",
        contextId = "remoteProviderService",
        fallbackFactory = ProviderFallbackFactory.class)
public interface RemoteProviderService {
    @GetMapping("/provider/echo/{msg}")
    String echoMessage(
            @PathVariable("msg") String msg);
}//consumer.controller-->Feign interface-->remote call
//起步依赖,自动配置-autoconfiguration,监控监控-actuator,嵌入式WEB-tomcat)
