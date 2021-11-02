package com.jt.blog.service;
import com.jt.blog.domain.Tag;
import java.util.List;
public interface TagService {
    /**
     * 查询所有的标签
     * @return
     */
    List<Tag> selectTags();

    /**
     * 创建一个新的tag对象
     * @param tag
     */
    void insertTag(Tag tag);

    /**
     * 更新tag对象
     * @param tag
     * @return
     */
    Tag updateTag(Tag tag);

    /**
     * 基于id查询tag信息
     * @param id
     * @return
     */
    Tag selectById(Long id);
}
