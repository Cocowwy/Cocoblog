package com.hncu.cocoblog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Cocowwy
 * @create 2020-05-05-23:19
 * 实体类Blog
 */
@Data
@NoArgsConstructor  //自动生成无参构造器
@AllArgsConstructor //全参构造器
public class Blog  {
    private Long id;   //主键
    private String title;  //标题
    private String content;  //内容
    private String firstPicture; //首图
    private String flag;  //博客标签
    private Integer views;  //浏览次数
    private boolean appreciation; //赞赏是否开启
    private boolean shareStatement; //转载申明是否开启
    private boolean commentabled ; //评论是否开启
    private boolean published;  //是否发布还是草稿
    private boolean recommend;  //是否推荐
    private Date createTime; //创建时间
    private Date updateTime; //更新时间
    private String description; //描述

    //这个属性用来在mybatis中进行连接查询的
    private Long typeId;
    private Long userId;

    private Type type;

    private List<Flag> tags=new ArrayList();

    private User user;



}
