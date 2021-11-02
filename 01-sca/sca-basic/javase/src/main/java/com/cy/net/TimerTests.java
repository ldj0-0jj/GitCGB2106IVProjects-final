package com.cy.net;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTests {
    public static void main(String[] args) {
        //构建一个timer对象负责执行任务调度
        Timer timer=new Timer();
        //执行任务调度(TimerTask表示任务类型)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis());
            }
        }, 1000,//1秒以后开始执行
        5000);//1每隔1秒执行一次
    }
}
