package com.jt.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.blog.domain.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JsonTests {
    @Test
    void testToJson01() throws JsonProcessingException {
        //定义对象
        Tag t=new Tag();
        t.setId(100L);
        t.setName("MySql");
        //将对象转换为Json格式字符串
        ObjectMapper objectMapper=new ObjectMapper();//jackson
        //将对象转换为json格式字符串时,会调用对象的get方法
        //将get单词后面的单词作为key,get方法的返回值作为value,拼接到json串中
        String jsonStr=objectMapper.writeValueAsString(t);
        System.out.println(jsonStr);
    }

    @Test
    void testToJson02() throws JsonProcessingException {
        List<Tag> list=new ArrayList<>();
        //定义对象
        Tag t=new Tag();
        t.setId(100L);
        t.setName("MySql");
        list.add(t);
        t=new Tag();
        t.setId(200L);
        t.setName("Redis");
        list.add(t);
        //将对象转换为Json格式字符串
        ObjectMapper objectMapper=new ObjectMapper();//jackson
        //将所有访问修饰符(private,public,protected,..)对应get方法
        //获取其名字和值作为json字符串的内容
        objectMapper.setVisibility(
                PropertyAccessor.ALL,
                JsonAutoDetect.Visibility.ANY);
        //激活默认类型(json串中存储序列化对象类型)
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        //将对象转换为json格式字符串时,会调用对象的get方法
        //将get单词后面的单词作为key,get方法的返回值作为value,拼接到json串中
        String jsonStr=objectMapper.writeValueAsString(list);
        System.out.println(jsonStr);
        //将json格式字符串执行反序列化
        List list1 =
        objectMapper.readValue(jsonStr, List.class);
        for(Object o:list1){
            System.out.println(o.getClass());
        }
        System.out.println(list1);
    }
}
