package com.hncu.cocoblog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**用来映射插叙的标签以及每个标签的总数
 * @author Cocowwy
 * @create 2020-05-05-23:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flag {
    private Long id;
    private String flag;
    private Integer sum;

    private List<Blog> blogs=new ArrayList<>();
}
