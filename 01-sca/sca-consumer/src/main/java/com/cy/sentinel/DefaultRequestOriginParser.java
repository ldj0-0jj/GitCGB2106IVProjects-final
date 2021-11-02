package com.cy.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 定义一个请求源解析对象,基于此对象对请求中指定数据进行解析,
 * 然后Sentinel底层会对此接口方法的返回值基于规则的定义进行
 * 处理.
 */
@Component
public class DefaultRequestOriginParser
        implements RequestOriginParser {
    //解析请求源数据
    @Override
    public String parseOrigin(HttpServletRequest request) {
        //获取请求参数数据,参数名可以自己写,例如origin,然后基于参数值做黑白名单设计
       // http://ip:port/path?origin=app1
         return request.getParameter("origin");

        //获取访问请求中的ip地址,基于ip地址进行黑白名单设计
        //String ip= request.getRemoteAddr();
        //System.out.println("ip="+ip);
        //return ip;

        //获取请求头中的数据,基于请求头中token值进行限流设计
        //String token=request.getHeader("token");//jack,tony
        //return token;
    }//授权规则中的黑白名单的值,来自此方法的返回值

}
