package com.jt.auth.pojo;

import lombok.Data;
import java.io.Serializable;

/**
 * 通过此对象封装从system服务获取的用户信息
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 4831304712151465443L;
    private Long id;
    private String username;
    private String password;
    private String status;
}