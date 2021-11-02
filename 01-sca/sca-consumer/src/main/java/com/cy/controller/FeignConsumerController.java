package com.cy.controller;
import com.cy.service.RemoteProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/consumer")
public class FeignConsumerController {

    @Autowired
    private RemoteProviderService remoteProviderService;

    /**执行远程服务调用
     * 访问:browser->consumer->provider
     * 1)browser
     * http://localhost:8090/consumer/echo/cgb
     * 2)consumer
     * http://sca-provider/provider/echo/cgb
     * */
    @GetMapping("/echo/{msg}")
    public String doRestEcho(@PathVariable("msg") String msg){

        return remoteProviderService.echoMessage(msg);
    }

}
