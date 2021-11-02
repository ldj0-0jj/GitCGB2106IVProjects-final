package com.cy.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GatewayConfig {
    public GatewayConfig(){
        GatewayCallbackManager.setBlockHandler(
                new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                //定义要响应的数据
                Map<String,Object> map=new HashMap<>();
                map.put("status", 429);
                map.put("message", "too many request");
                //将map对象转换为json格式字符串(借助alibaba的fastjson中的JSON对象实现)
                String jsonStr=JSON.toJSONString(map);
                //封装响应数据并返回
                return ServerResponse.ok().body(
                        Mono.just(jsonStr),//将响应数据存储到一个响应序列中
                        String.class);//这里的String.class为响应到客户端的数据类型
            }//基于 spring webflux
        });
    }
}
