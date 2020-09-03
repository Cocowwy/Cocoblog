package com.hncu.cocoblog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cocowwy
 * @create 2020-05-05-23:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    private Long id;
    private String name;
    private List<Blog> blogs=new ArrayList<>();
}
