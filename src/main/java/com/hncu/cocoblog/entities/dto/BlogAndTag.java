package com.hncu.cocoblog.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cocowwy
 * @create 2020-05-05-23:36
 */
//保存博客和标签之间的关系
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogAndTag {
    private Long tagId;
    private Long blogId;
}
