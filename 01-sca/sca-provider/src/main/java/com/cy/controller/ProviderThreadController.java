package com.cy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ProviderThreadController {
    @Value("${server.tomcat.threads.max:200}")
    private Integer maxThread;

    @RequestMapping("/provider/doGetMaxThread")
    public String doGetMaxThread(){
        return "server.threads.max is  "+maxThread;
    }
}
