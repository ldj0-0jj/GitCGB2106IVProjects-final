package com.jt.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.system.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 基于用户名查询用户信息，此方法服务登录操作
     * @param username 这个值来自客户端用户的输入
     * @return 查询到的用户信息
     */
     @Select("select id,username,password,status " +
             "from tb_users where username=#{username}")
     User selectUserByUsername(String username);

    /**
     * 基于用户id查询用户权限(这里比较难)
     * @param userId
     * @return
     */
    @Select("select distinct m.permission " +
            "from tb_user_roles ur join tb_role_menus rm " +
            "     on ur.role_id=rm.role_id " +
            "     join tb_menus m " +
            "     on rm.menu_id=m.id " +
            "where ur.id=#{userId}")
     List<String> selectUserPermissions(Long userId);

}
