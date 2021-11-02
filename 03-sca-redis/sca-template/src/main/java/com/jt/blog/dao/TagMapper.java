package com.jt.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.blog.domain.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper
        extends BaseMapper<Tag> {
}
