package com.cy.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="sca-provider")
public interface RemoteOtherService {
    //.......
//    @GetMapping("/doSomeThing")
//    public String doSomeThing();
}
