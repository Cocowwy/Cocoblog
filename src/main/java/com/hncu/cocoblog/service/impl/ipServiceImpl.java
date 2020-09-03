package com.hncu.cocoblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hncu.cocoblog.dao.blogDao;
import com.hncu.cocoblog.dao.ipDao;
import com.hncu.cocoblog.entities.AccessIP;
import com.hncu.cocoblog.entities.Blog;
import com.hncu.cocoblog.service.ipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Cocowwy
 * @create 2020-05-05-22:46
 */
@Service
public class ipServiceImpl implements ipService {
    @Autowired
    ipDao ipDao;

    /**
     * 保存该ip访问该bolg的信息，前提是该ip未访问过blog
     *
     * @param accessIP
     */
    @Override
    public void saveAccessWithIP(AccessIP accessIP) {
        ipDao.saveAccessWithIP(accessIP);
    }

    /**
     * 根据ip以及博客id来查 如果能找到记录 就说明该IP访问过了该博客
     *
     * @param ip     参数为IP
     * @param blogId 该IP访问的博客的id
     * @return 返回查询到的记录
     */
    @Override
    public AccessIP getAccessWithIP(String ip, Integer blogId) {
        return ipDao.getAccessWithIP(ip, blogId);
    }

    /**
     * 上述如果查到了就对原纪录进行加一的操作
     *
     * @param id 参数为需要进行加一的记录的id
     */
    @Override
    public Integer updateThisIPViews(Integer id, Date date) {
        return ipDao.updateThisIPViews(id, date);
    }

    //查询总浏览次数
    @Override
    public Integer getAllViews() {
        return ipDao.getAllViews();
    }

    //多表查询得到ip访问的博客的信息
    @Override
    public List<AccessIP> getAccessIPListWithBlogTitle() {
        return ipDao.getAccessIPListWithBlogTitle();
    }

    //ip页面的分页
    @Override
    public PageInfo IPAccessPageInfo(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<AccessIP> list = ipDao.getAccessIPListWithBlogTitle();
        PageInfo<AccessIP> pageInfo = new PageInfo<AccessIP>(list);
        return pageInfo;
    }

    //查询指定ip的记录
    @Override
    public List<AccessIP> searchAccessByIP(String ip) {
        return ipDao.searchAccessByIP(ip);
    }





}
