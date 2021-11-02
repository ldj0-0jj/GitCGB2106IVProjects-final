package com.jt.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 此对象用于封装用户信息
 * Java中所有用于存储数据的对象都建议实现序列化接口并添加一个序列化id
 */
@Data
//@TableName("tb_users")
public class User implements Serializable {
    private static final long serialVersionUID = 4831304712151465443L;
    //@TableId(type=IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String status;
}
