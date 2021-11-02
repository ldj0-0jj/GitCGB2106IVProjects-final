package com.jt.blog.dao;

import com.jt.blog.domain.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TagMapperTests {
    @Autowired
    private TagMapper tagMapper;
    @Test
    void testSelectList(){
        List<Tag> tags =
        tagMapper.selectList(null);
        for(Tag t:tags){
            System.out.println(t);
            //System.out.println(t.getId()+"/"+t.getName());
        }
    }
}
