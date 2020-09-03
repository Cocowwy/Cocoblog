package com.hncu.cocoblog.service;

import com.github.pagehelper.PageInfo;
import com.hncu.cocoblog.entities.Blog;
import com.hncu.cocoblog.entities.Flag;
import com.hncu.cocoblog.entities.User;

import java.util.List;

/**
 * @author Cocowwy
 * @create 2020-05-05-19:41
 */
public interface blogService {
    //保存博客
    int saveBlog(Blog blog);

    //查询全部博客
    List<Blog> queryBlog();

    //分页
    PageInfo findPage(int page,int pageSize);

    //更新
    void updateBlog(Blog blog);

    //删除Blog
    int  deleteBlog(Long id);

    //得到总记录数
    Integer getBlogCount();

    //根据浏览次数降序得到博客结果集
    List<Blog> blogOrderByViews();

    //分页 对查询全部博客的结果进行分页
    PageInfo findPageOrderByViews(int page, int pageSize);

    //查询总浏览次数
    Integer getAllViews();

    //模糊查询 主页面搜索功能
    List<Blog> getBlogBySearch(String searchMessage);

    //通过id查询blog
    Blog getBlogById(Integer id);

    //插叙各个标签所含有的文章数
    List<Flag> getEveryFlagCounts();

    //通过标签得到博客集合
    List<Blog> getBlogListByFlag(String flag);

    //对指定的blogid的vies加1
    void addViewById(Integer BlogId);

    //按照创建的时间排序返回的blog集合
    List<Blog> getBlogListByCreateTime();

    //通过username查询user信息
    User getUserByUsername(String username);

}
