package com.jt.blog.service;

import com.jt.blog.domain.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TagServiceTests {
    @Autowired
    private TagService tagService;

    @Test
    void testUpdateTag(){
        Tag tag=new Tag();
        tag.setId(1L);
        tag.setName("mysql9.0");
        tagService.updateTag(tag);
    }
    @Test
    void testSelectById(){
        Tag tag = tagService.selectById(1L);
        System.out.println(tag);
    }

    @Test
    void testInsertTag(){
        Tag tag=new Tag();
        tag.setName("Oracle1");
        tagService.insertTag(tag);
    }
    
    @Test
    void testSelectTags(){
        List<Tag> tags=
        tagService.selectTags();
        System.out.println(tags);
    }
}
