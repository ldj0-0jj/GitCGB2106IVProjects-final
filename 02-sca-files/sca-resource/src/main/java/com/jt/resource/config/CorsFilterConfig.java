package com.jt.resource.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * 跨域配置(基于过滤器方式进行配置，并且将过滤优先级设置高一些)
 */
//@Configuration
public class CorsFilterConfig {
    @Bean
    public FilterRegistrationBean<CorsFilter> filterFilterRegistrationBean(){
        //1.对此过滤器进行配置(跨域设置-url,method)
        UrlBasedCorsConfigurationSource configSource=new UrlBasedCorsConfigurationSource();
        CorsConfiguration config=new CorsConfiguration();
        //允许哪种请求头跨域
        config.addAllowedHeader("*");
        //允许哪种方法类型跨域 get post delete put
        config.addAllowedMethod("*");
        // 允许哪些请求源(ip:port)跨域
        config.addAllowedOrigin("*");
        //是否允许携带cookie跨域
        config.setAllowCredentials(true);
        //2.注册过滤器并设置其优先级
        configSource.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> fBean= new FilterRegistrationBean(new CorsFilter(configSource));
        fBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return fBean;
    }
}

