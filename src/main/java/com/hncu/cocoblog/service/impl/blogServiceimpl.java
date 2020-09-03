package com.hncu.cocoblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hncu.cocoblog.dao.blogDao;
import com.hncu.cocoblog.entities.Blog;
import com.hncu.cocoblog.entities.Flag;
import com.hncu.cocoblog.entities.User;
import com.hncu.cocoblog.service.blogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Cocowwy
 * @create 2020-05-05-19:43
 */
@Service
public class blogServiceimpl implements blogService {
    @Autowired
    blogDao blogDao;

    //保存博客
    @Override
    public int saveBlog(Blog blog) {
        return blogDao.saveBlog(blog);
    }


    //查询全部博客
    @Override
    public List<Blog> queryBlog() {
        return blogDao.queryBlog();
    }

    //分页 对查询全部博客的结果进行分页
    @Override
    public PageInfo findPage(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Blog> list = blogDao.queryBlog();
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(list);
        return pageInfo;
    }

    //更新
    @Override
    public void updateBlog(Blog blog) {
        blogDao.updateBlog(blog);
    }


    //删除博客
    @Override
    public int deleteBlog(Long id) {
        return blogDao.deleteBlog(id);

    }

    //得到博客总数
    @Override
    public Integer getBlogCount() {
        return blogDao.getBlogCount();
    }

    //根据浏览次数降序得到博客列表
    @Override
    public List<Blog> blogOrderByViews() {
        return blogDao.blogOrderByViews();
    }

    //分页 对查询全部博客的结果进行分页
    @Override
    public PageInfo findPageOrderByViews(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Blog> list = blogDao.blogOrderByViews();
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(list);
        return pageInfo;
    }

    //查询总浏览次数
    @Override
    public Integer getAllViews() {
        return blogDao.getAllViews();
    }

    //通过搜索模糊查询博客
    @Override
    public List<Blog> getBlogBySearch(String searchMessage) {
        return blogDao.getBlogBySearch("%" + searchMessage + "%"); //模糊查询
    }

    //通过id查找博客
    @Override
    public Blog getBlogById(Integer id) {
        return blogDao.getBlogById(id);
    }

    //插叙各个标签所含有的文章数
    @Override
    public List<Flag> getEveryFlagCounts() {
        return blogDao.getEveryFlagCounts();
    }

    //通过标签得到博客集合
    @Override
    public List<Blog> getBlogListByFlag(String flag) {
        return blogDao.getBlogListByFlag(flag);
    }

    //对指定的blogid的vies加1
    @Override
    public void addViewById(Integer BlogId) {
        blogDao.addViewById(BlogId);
    }

    //按照创建的时间排序返回的blog集合
    @Override
    public List<Blog> getBlogListByCreateTime() {
        return blogDao.getBlogListByCreateTime();
    }

    //通过username查询user信息
    @Override
    public User getUserByUsername(String username){
        return blogDao.getUserByUsername(username);
    };
}
