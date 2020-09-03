package com.hncu.cocoblog.dao;

import com.hncu.cocoblog.entities.AccessIP;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Cocowwy
 * @create 2020-05-05-21:20
 */
@Repository
@Mapper
public interface ipDao {
    /**
     * 保存该ip访问该bolg的信息，前提是该ip未访问过blog
     *
     * @param accessIP
     */
    void saveAccessWithIP(AccessIP accessIP);

    /**
     * 根据ip以及博客id来查 如果能找到记录 就说明该IP访问过了该博客
     *
     * @param ip     参数为IP
     * @param blogId 该IP访问的博客的id
     * @return 返回查询到的记录
     */
    AccessIP getAccessWithIP(String ip, Integer blogId);

    /**
     * 上述如果查到了就对原纪录进行加一的操作
     *
     * @param id 参数为需要进行加一的记录的id
     */
    Integer updateThisIPViews(Integer id, Date date);

    //查询总浏览记录数
    Integer getAllViews();

    //多表查询得到ip访问的博客的信息
    List<AccessIP> getAccessIPListWithBlogTitle();


    //多表查询得到ip访问的博客的信息 再得到指定ip的
    List<AccessIP> searchAccessByIP(String ip);

}
