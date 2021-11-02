package com.jt.blog.controller;

import com.jt.blog.domain.Tag;
import com.jt.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    //private List<Tag> tags=new ArrayList<>();
    private List<Tag> tags=new CopyOnWriteArrayList<>();//本地 cache


    @GetMapping("/{id}")
    public Tag doSelectById(@PathVariable("id") Long id){
        //查询本地缓存
        for(Tag t:tags){
            if(t.getId().equals(id)) return t;
        }
        //查询redis,mysql
        return tagService.selectById(id);
    }

    @PostMapping
    public String doInsertTag(Tag tag){
        //向数据库写入数据
        tagService.insertTag(tag);//A
        //更新本地缓存
        tags.add(tag);
        return "insert ok";
    }

    @PutMapping
    public String doUpdateTag(Tag tag){//id=1,name=mysql 8.9
        //向数据库写入数据
        tagService.updateTag(tag);
        //更新本地缓存
        for(Tag t:tags){
            if(t.getId().equals(tag.getId())){
                t.setName(tag.getName());
            }
        }
        return "update ok";
    }


    @GetMapping
    public  List<Tag> doSelectTags(){//B
       if(tags.isEmpty()) {
           synchronized (tags) {
               if(tags.isEmpty()) {
                   tags.addAll(tagService.selectTags());//1.redis,2.mysql
               }
           }
       }
       return tags;
    }
    /**Spring中Bean对象的生命周期方法,对象初始化时执行此方法*/
    @PostConstruct
    public void doInit(){
        doTimerRefreshTask();
    }
    /**Spring中Bean对象的生命周期方法,Bean对象初始化时执行此方法*/
    @PreDestroy
    public void doDestory(){
        //退出定时任务
        timer.cancel();
    }
    private Timer timer;
    //定义刷新任务
    private void doTimerRefreshTask(){
        //构建一个定时任务调度对象
        timer=new Timer();
        //构建一个任务对象
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                System.out.println("refresh cache");
                tags.clear();
            }
        };
        //执行任务对象(每隔5秒执行一次)
        timer.schedule(task, 5000, 5000);
    }

}
//redis  分布式缓存
//Tomcat 本地缓存(JVM内部提供)
//Browser(查询)-->Nginx-->Gateway,Gateway,..-->(Tomcat,Tomcat,Tomcat)
//查询-->本地cache-->分布式cache-->mysql