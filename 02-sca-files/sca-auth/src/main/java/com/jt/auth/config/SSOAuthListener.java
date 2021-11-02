package com.jt.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 为什么写这个对象,这个对象的作用是什么?
 * ProviderManager中进行断点分析时,发现登录成功,会调用
 * AuthenticationEventPublisher中的publishAuthenticationSuccess
 * 方法,所以我们尝试在这个方法中做一个简单的日志记录.
 */
@Slf4j
@Component
public class SSOAuthListener extends DefaultAuthenticationEventPublisher {
    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        super.publishAuthenticationSuccess(authentication);
        UserDetails principal =
                (UserDetails) authentication.getPrincipal();
        log.info("{} login success {}",principal.getUsername(),LocalDateTime.now());
        //remoteLogService.saveLog(xxxx);
    }
    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        super.publishAuthenticationFailure(exception, authentication);
        log.info("login failure {} {}",LocalDateTime.now(), exception.getMessage());
    }

}
