package com.hncu.cocoblog;

import com.hncu.cocoblog.dao.blogDao;
import com.hncu.cocoblog.dao.ipDao;
import com.hncu.cocoblog.entities.AccessIP;
import com.hncu.cocoblog.entities.Blog;
import com.hncu.cocoblog.entities.Flag;
import com.hncu.cocoblog.service.blogService;
import com.hncu.cocoblog.service.ipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class CocoblogApplicationTests {
    @Autowired
    blogService blogService;

    @Autowired
    blogDao blogDao;

    @Autowired
    ipDao ipDao;

    @Autowired
    ipService ipService;


    @Test
    void testGetBlogBySearch() {
        List<Blog> mes = blogService.getBlogBySearch("页面");
        System.out.println(mes);
    }

    @Test
    void testGetBlogById() {
        Blog blogById = blogService.getBlogById(2);
        System.out.println(blogById);
    }

    @Test
    void testGetEveryFlagCounts() {
        List<Flag> everyFlagCounts = blogService.getEveryFlagCounts();
        System.out.println(everyFlagCounts);
    }

    @Test
    void testGetBlogListByFlag() {
        List<Blog> java = blogService.getBlogListByFlag("Java");
        System.out.println(java);
    }

    @Test
    void testGetAccessWithIP() {
        AccessIP accessWithIP = ipDao.getAccessWithIP("192.111.111.111", 1);
        System.out.println(accessWithIP);
    }

    @Test
    void testSaveAccessWithIP() {
        AccessIP accessIP = new AccessIP();
        accessIP.setViews(1);
        accessIP.setBlogId(2);
        accessIP.setLastAccess(new Date());
        accessIP.setIp("111");

        ipDao.saveAccessWithIP(accessIP);
    }

    @Test
    void testAddViewById() {
        blogService.addViewById(2);
    }

    @Test
    void testGetAccessIPListWithBlogTitle() {
        System.out.println(ipService.getAccessIPListWithBlogTitle());
    }

    @Test
    void testSearchAccessByIP(){
        System.out.println(ipDao.searchAccessByIP("113.223.118.44"));
    }

    @Test
    void testGetUserByUsername(){
        System.out.println(blogDao.getUserByUsername("coco"));
    }
}
