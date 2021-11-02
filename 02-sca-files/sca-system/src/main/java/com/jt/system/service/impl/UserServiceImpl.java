package com.jt.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.system.dao.UserMapper;
import com.jt.system.pojo.User;
import com.jt.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByUsername(String username) {
//        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("username",username);
//        return userMapper.selectOne(queryWrapper);
       return userMapper.selectUserByUsername(username);
    }

    @Override
    public List<String> selectUserPermissions(Long userId) {
        //1)单表多次查询 (表不在一个数据库时)
        //List<Integer> roleIds=userMapper.selectRoleIdsByUserId(userId);
        //List<Integer> menuIds=userMapper.selectMenuIdsByRoleIds(roleIds);
        //List<String> permissions=userMapper.selectPermissionsByMenuId(menuIds);
        //2)多表嵌套查询(多个表在一个数据库,不同数据库表现出性能不一样,mysql表现比较差)
        //3)多表关联查询(多个表在一个数据库,基于外键字段关联,
        //但现阶段的表设计中的外键通常是逻辑外键,本次应用方案)
        return userMapper.selectUserPermissions(userId);
    }
}
