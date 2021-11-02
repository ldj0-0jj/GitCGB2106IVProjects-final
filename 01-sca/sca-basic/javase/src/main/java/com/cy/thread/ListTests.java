package com.cy.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListTests {
    //static List<Integer> cache=new ArrayList<>();
    //CopyOnWriteArrayList对象是一个线程安全(乐观锁)的list集合
    //这个集合在修改集合内容时,会先将集合内容拷贝到线程自己的内存区域,然后修改,
    //修改后,再将私有区域的数据写会到共享内存区域(假如原有集合内容已经修改过,则修改失败)
    static List<Integer> cache=new CopyOnWriteArrayList<>();
    static  List<Integer> selectData(){
        if(cache.isEmpty()) {
            synchronized (cache) {//同步代码块
                if (cache.isEmpty()) {//t1,t2,t3 [100],[100,100],[100,100,100],...
                    cache.add(100);
                }
            }//保证业务的原子性
        }
        return cache;
    }//保证线程安全,还要考虑其性能(减少阻塞).

    public static void main(String[] args) {
       Thread t1=new Thread(()->{
           System.out.println(selectData());
       });
       Thread t2=new Thread(()->{
            System.out.println(selectData());
       });
       Thread t3=new Thread(()->{
            System.out.println(selectData());
       });
       t1.start();
       t2.start();
       t3.start();
    }
}
