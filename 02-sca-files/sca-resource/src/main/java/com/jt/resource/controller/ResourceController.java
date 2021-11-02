package com.jt.resource.controller;

import com.jt.resource.annotation.RequiredLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Slf4j
//@CrossOrigin //用于在controller层面处理跨域
@RefreshScope //假如属性的值来自配置中心,配置中心内容变化,属性值也要变就需要添加上此注解
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Value("${jt.resource.path:g:/uploads}")
    private String resourcePath;

    @Value("${jt.resource.host:http://localhost:8881/}")
    private String resourceHost;

    /**
     * 通过此方法处理文件上传的请求
     * @param uploadFile 接收要上传的文件数据(参数名一定要与客户端提交的名字一样)
     * @return 文件上传以后在服务端的实际存储路径,可以基于http协议访问这个文件
     */
    @PreAuthorize("hasAuthority('sys:res:create')")
    @RequiredLog(value="文件上传") //此注解描述的方法为切入点方法
    @PostMapping("/upload/")//文件上传的请求方式必须是post
    public String uploadFile(MultipartFile uploadFile) throws IOException {
        //log.info("before {}", System.currentTimeMillis());
        //1.创建文件的存储目录(按年月日的结构进行存储)
        //1.1获取当前日期对应的字符串
        //1.1.1方式1
        //SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        //String dateStr=sdf.format(new Date());
        //1.1.2方式2(基于jdk8中提供的日期API)

        String dateStr =
                DateTimeFormatter.ofPattern("yyyy/MM/dd")
                        .format(LocalDate.now());
        log.debug("date dir is {}",dateStr);

        //1.2创建文件目录对象
        File uploadDir=
                new File(resourcePath,dateStr);//g:/uplods/2021/09/24
        if(!uploadDir.exists())
                uploadDir.mkdirs();

        //2.给文件一个新的名字(文件前缀随机产生,文件后缀不能变)
        //2.1获取原始文件名
        String originalFilename =
                uploadFile.getOriginalFilename();
        //2.2构建文件前缀
        String filePrefix=
                UUID.randomUUID().toString();
        //2.2获取文件后缀 xxx.png
        String fileSuffix=
        originalFilename.substring(
                originalFilename.lastIndexOf("."));
        //2.3构建新文件名
        String newFileName=filePrefix+fileSuffix;

        //3.上传文件到指定目录
        //transferTo方法底层会做什么?(文件复制)
        //基于inputStream读取uploadFile中的内容
        //基于OutputStream将读取的内容写到新的文件中
        uploadFile.transferTo(new File(uploadDir,newFileName));

        //4.返回通过http协议可以访问到这个文件的路径
        //String accessPath="http://localhost:8881/2021/09/24/xx.png";
        String accessPath=resourceHost+dateStr+"/"+newFileName;
        log.info("access path is {}", accessPath);

        //log.info("After {}", System.currentTimeMillis());
        return accessPath;
    }
}
//业务
//1)核心业务
//2)拓展业务(日志,缓存,权限)
//2.1)在方法中硬编码(直接将拓展业务添加到原有业务方法)
//2.2)采用继承或组合方式实现


