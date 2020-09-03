package com.hncu.cocoblog.dao;


import com.hncu.cocoblog.entities.Blog;
import com.hncu.cocoblog.entities.Flag;
import com.hncu.cocoblog.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Cocowwy
 * @create 2020-05-05-0:14
 */
@Repository
@Mapper
public interface blogDao {
    //存储Blog
    int saveBlog(Blog blog);

    //分页查询博客
    List<Blog> queryBlog();

    //删除Blog
    int deleteBlog(Long id);

    //得到总记录数
    Integer getBlogCount();

    //根据浏览次数降序得到博客结果集
    List<Blog> blogOrderByViews();

    //查询总浏览次数
    Integer getAllViews();

    //模糊查询 主页面搜索功能
    List<Blog> getBlogBySearch(String searchMessage);

    //根据id查找博客
    Blog getBlogById(Integer id);

    //更新Blog
    int updateBlog(Blog blog);

    //插叙各个标签所含有的文章数
    List<Flag> getEveryFlagCounts();

    //通过标签得到博客的集合
    List<Blog> getBlogListByFlag(String flag);

    //对指定的blogid的vies加1
    void addViewById(Integer BlogId);

    //按照创建的时间排序返回的blog集合
    List<Blog> getBlogListByCreateTime();

    //通过username查询user信息
    User getUserByUsername(String username);
}
