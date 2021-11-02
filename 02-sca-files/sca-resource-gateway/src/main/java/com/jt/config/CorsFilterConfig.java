package com.jt.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

//@Configuration
public class CorsFilterConfig {
    @Bean
    public CorsWebFilter corsWebFilter(){
        //1.构建基于url方式的跨域配置
        UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
        //2.进行跨域配置
        CorsConfiguration config=new CorsConfiguration();
        //2.1允许所有ip:port进行跨域
        config.addAllowedOrigin("*");
        //2.2允许所有请求头跨域
        config.addAllowedHeader("*");
        //2.3允许所有请求方式跨域:get,post,..
        config.addAllowedMethod("*");
        //2.4允许携带有效cookie进行跨域
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**",config);
        return new CorsWebFilter(source);
    }
}
//跨域分析

//1)@CrossOrigin 可以从controller层面解决单个controller跨域问题

//2)服务中的CorsFilter 这个spring webmvc中给出过滤器层面的跨域,
//可以解决多个controller的跨域问题

//3)网关中的CorsWebFilter 这个是spring webflux中的过滤器,
//可以从网关层面解决多个服务的跨域问题,这就不需要每个服务都写一遍跨域了