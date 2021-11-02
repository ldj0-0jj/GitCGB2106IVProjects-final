package com.jt.blog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 标签类的设计
 */
@TableName("tb_tag")
public  class Tag implements Serializable {
    private static final long serialVersionUID = 4504013456197711455L;
    /**标签id*/
    @TableId(type = IdType.AUTO)
    private Long id;
    /**标签名*/
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
