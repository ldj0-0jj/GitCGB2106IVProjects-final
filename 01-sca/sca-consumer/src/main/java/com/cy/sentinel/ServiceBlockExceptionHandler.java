package com.cy.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**自定义限流异常处理对象
 * 为什么要自己定义?
 * 默认的异常处理规则不能满足我们需要,此时我们就自己定义*/
@Component
public class ServiceBlockExceptionHandler
        implements BlockExceptionHandler {
    /**一旦服务被限流或降级了,sentinel系统底层提供的拦截器会
     * 调用此方法对异常进行处理*/
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       BlockException e) throws Exception {
        //方案1:异常继续抛出
        //throw e;
        //方案2:对异常内容进行处理(例如返回给客户端一个可以看得懂的信息)
        //思考:
        //1)向客户端输出数据(写数据)用什么?输出流对象
        //2)如何获取输出流对象?(标准的JAVAEE规范中,要借助HttpServletResponse对象)
        //3)当向客户端输出数据时,除了响应业务数据外,还要做什么?
        //设置响应数据的编码
        httpServletResponse.setCharacterEncoding("utf-8");
        //告诉浏览器服务端向它响应的数据类型,以及以什么编码进行显示.
       // httpServletResponse.setContentType("text/html;charset=utf-8");
        httpServletResponse.setContentType(
                "application/json;charset=utf-8");
        //响应业务数据
        PrintWriter out=httpServletResponse.getWriter();
        Map<String,Object> map=new HashMap<>();
        map.put("status", 429);
        if(e instanceof DegradeException){//DegradeException降级异常
           // out.println("<h2>服务暂时不可用</h2>");
          map.put("message","服务暂时不可用");
        }else{
           //out.println("<h2>访问太频繁,稍等片刻再访问</h2>");
          map.put("message","访问太频繁,稍等片刻再访问");
        }
        //将map对象转换为json格式字符串
        String jsonStr=
        new ObjectMapper().writeValueAsString(map);
        //{"status":429,"message":"..."}
        out.println(jsonStr);
        out.flush();//所有字符流都要刷新
    }
}
