package com.jt.auth.service;

import com.jt.auth.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "sca-system", contextId ="remoteUserService" )
public interface RemoteUserService {

    @GetMapping("/user/login/{username}")
    User selectUserByUsername(
            @PathVariable("username") String username);

    @GetMapping("/user/permission/{userId}")
    List<String> selectUserPermissions(
            @PathVariable("userId") Long userId);
}
