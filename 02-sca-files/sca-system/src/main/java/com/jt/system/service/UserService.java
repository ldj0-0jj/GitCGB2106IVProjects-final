package com.jt.system.service;
import com.jt.system.pojo.User;
import java.util.List;

/**
 * 用户业务接口
 */
public interface UserService {
    /**
     * 基于用户名查询用户信息,这个信息可以交给认证中心,
     * 然后认证中心基于客户端输入的用户信息以及数据库端
     * 提供的数据信息,进行比对认证.
     * @param username
     * @return
     */
    User selectUserByUsername(String username);

    /**
     * 基于登录用户id查询用户权限,查询方案有很多种,例如:
     * 1)单表多次查询
     * 2)多表嵌套查询
     * 3)多表关联查询
     * @param userId 登录用户id
     * @return 用户权限 sys:res:create,sys:res:delete,...
     */
    List<String> selectUserPermissions(Long userId);
}
