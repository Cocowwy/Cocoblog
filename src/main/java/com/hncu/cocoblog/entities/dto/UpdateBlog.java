package com.hncu.cocoblog.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Cocowwy
 * @create 2020-05-05-23:46
 */
//博客修改之后的类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBlog {
    private Long id;   //主键
    private String title;  //标题
    private String content;  //内容
    private String firstPicture; //首图
    private String flag;  //博客标签
    private boolean appreciation; //赞赏是否开启
    private boolean shareStatement; //转载申明是否开启
    private boolean commentabled ; //评论是否开启
    private boolean published;  //是否发布还是草稿
    private boolean recommend;  //是否推荐
    private Date updateTime; //更新时间
    private String description; //描述
}
