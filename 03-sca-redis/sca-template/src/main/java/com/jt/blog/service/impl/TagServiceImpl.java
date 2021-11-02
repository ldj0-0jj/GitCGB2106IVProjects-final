package com.jt.blog.service.impl;

import com.jt.blog.dao.TagMapper;
import com.jt.blog.domain.Tag;
import com.jt.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    //RedisAutoConfiguration 类中做的RedisTemplate的配置
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TagMapper tagMapper;

    /**
     * 向数据库中再添加一个新的标签
     * @param tag
     * 思考:假如这里做了insert操作后,数据库中的数据更新了,
     * 也要更新redis缓存,你有什么方案?
     * 方案1:不更新redis,等redis的key失效后,自动更新.
     * 方案2:执行完insert,删除redis指定的key对应的数据
     * 方案3:执行完insert,更新redis指定key对应的内容
     * 说明:
     * @CacheEvict注解的作用是定义缓存切入点方法,执行此注解描述的方法
     * 时,底层通过AOP方式执行redis数据的清除操作.
     */
    @CacheEvict(value = "tagCache",
                allEntries = true,//删除key对应的所有数据
                beforeInvocation = false)//执行insert之后,删除缓存数据
    @Override
    public void insertTag(Tag tag) {
        tagMapper.insert(tag);
    }

//    @Override
//    public void insertTag(Tag tag){
//        //写mysql数据库
//        tagMapper.insert(tag);
//        //更新redis数据库
//        ValueOperations<String,List<Tag>> vo =
//          redisTemplate.opsForValue();
//        String key="tagCache::com.jt.blog.service.impl.TagServiceImpl::selectTags";
//        List<Tag> list=vo.get(key);
//        list.add(tag);
//        vo.set(key,list);
//    }

    /**
     * @CachePut 注解描述的方法为一个缓存切入点方法,
     * 系统底层在执行此注解描述的方法时,可以对缓存数据
     * 执行更新操作.(底层会先基于key获取值,然后对值进行修改)
     * @param tag
     */
      @CacheEvict(value = "tagCache",
            allEntries = true,//删除key对应的所有数据
            beforeInvocation = false)
      @CachePut(value = "tagCache",key="#tag.id")
      @Override
      public Tag updateTag(Tag tag){
          tagMapper.updateById(tag);
          return tag;
      }

      @Cacheable(value="tagCache",key="#id")
      @Override
      public Tag selectById(Long id){
          return tagMapper.selectById(id);
      }

     /*@Cacheable注解描述的方法为缓存切入点方法
      *访问此方法时系统底层会先从缓存查找数据,假如缓存缓存没有,
      *会查询mysql数据库,这个注解假如想生效需要在启动类或者配置
      *类上添加@EnableCaching注解.
      *其中,这里的value用于指定一个key前缀,
      *没有指定key属性,则默认会使用 KeyGenerator对象创建key
     */
     @Cacheable(value = "tagCache")
     @Override
     public List<Tag> selectTags() {
        return tagMapper.selectList(null);
     }

//    @Override
//    public List<Tag> selectTags() {
//        System.out.println("service.selectTags()");
//        //1.从redis查询Tag信息,redis有则直接返回
//        ValueOperations<String,List<Tag>> valueOperations =
//        redisTemplate.opsForValue();
//        List<Tag> tags=valueOperations.get("tags");
//        if(tags!=null&&!tags.isEmpty())return tags;
//        //2.从redis没有获取tag信息，查询mysql
//        tags = tagMapper.selectList(null);
//        //3.将从mysql查询到tag信息存储到redis
//        valueOperations.set("tags", tags);
//        //4.返回查询结果
//        return tags;
//    }

}
