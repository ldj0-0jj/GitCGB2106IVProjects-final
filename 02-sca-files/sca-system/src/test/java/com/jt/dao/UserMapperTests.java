package com.jt.dao;

import com.jt.system.dao.UserMapper;
import com.jt.system.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelectUserByUserName(){
        User admin =
                userMapper.selectUserByUsername("admin");
        System.out.println(admin);
    }
    @Test
    void testSelectUserPermissions(){
        List<String> permissions =
                userMapper.selectUserPermissions(1L);
        System.out.println(permissions);
    }
}
