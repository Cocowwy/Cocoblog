package com.hncu.cocoblog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用来封装IP访问bolg的一些信息
 *
 * @author Cocowwy
 * @create 2020-05-05-21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessIP {
    Integer id; //对应主键
    String ip; //对应IP
    Integer blogId;//访问的博客
    Integer views;//该IP访问该blogId的次数
    Date lastAccess;//用来保存最后一次访问该博客的时间
    String blogTitle;//表示访问的博客的标题
}
