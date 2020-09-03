package com.hncu.cocoblog.controller;


import com.github.pagehelper.PageInfo;
import com.hncu.cocoblog.entities.AccessIP;
import com.hncu.cocoblog.entities.Blog;
import com.hncu.cocoblog.service.blogService;
import com.hncu.cocoblog.service.ipService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.jws.WebParam;
import java.util.Date;
import java.util.List;

/**
 * @author Cocowwy
 * @create 2020-05-05-0:36
 */
@Controller
@Transactional(rollbackFor = Exception.class) //设置全局对exception异常的回滚
public class Blogcontroller {
    @Autowired
    blogService blogService;

    @Autowired
    ipService ipService;

    //存储博客
    @GetMapping("/saveBlog")
    public String saveBlog(Blog blog, Model model) {
        blog.setCreateTime(new Date()); //设置博客创建的时间
        blog.setPublished(true); //设置为发布
        blog.setViews(0);  //设置浏览次数
        blogService.saveBlog(blog);
        return "redirect:/homePage";  //跳转到homePage页面
    }

    // 分页查询博客
    @GetMapping("/getBlogByPage/{page}")
    public String getBlogByPage(@PathVariable("page") Integer page, Model model) {
        PageInfo<Blog> pageInfo = blogService.findPage(page, 8); //获取指定页的8条信息
        //这些信息都需要传递到前端做分页
        model.addAttribute("pageNum", pageInfo.getPageNum());//得到当前页
        model.addAttribute("pages", pageInfo.getPages()); //得到总页数
        model.addAttribute("list", pageInfo.getList()); //将分页得到的list集合返回到前端
        return "manageBlog";
    }

    // 按照浏览次数分页查询博客
    @GetMapping("/getBlogByPageOrderByViews/{page}")
    public String getBlogByPageOrderByViews(@PathVariable("page") Integer page, Model model) {
        PageInfo<Blog> pageInfo = blogService.findPageOrderByViews(page, 8); //获取指定页的8条信息
        PageInfo<Blog> pageInfoModel = blogService.findPageOrderByViews(1, 4); //页面动态首图
        //这些信息都需要传递到前端做分页
        model.addAttribute("pageNum", pageInfo.getPageNum());//得到当前页
        model.addAttribute("pages", pageInfo.getPages()); //得到总页数
        model.addAttribute("list", pageInfo.getList()); //将分页得到的list集合返回到前端odel
        model.addAttribute("listModel", pageInfoModel.getList()); //将分页得到的list集合返回到前端odel
        model.addAttribute("total", pageInfo.getTotal()); //得到总记录数
        model.addAttribute("views", ipService.getAllViews()); //得到总浏览次数
        model.addAttribute("flags", blogService.getEveryFlagCounts());//得到每个标签的博文的数目

        System.out.println(page);
        return "homePage";
    }

    //删除  注意转发回原页面后 要Model，不然没model  同时还需要知道当前页数
    @GetMapping("/deleteBlog/{id}/{pageNum}")
    public String deleteBlog(@PathVariable("id") long id, @PathVariable("pageNum") Integer pageNum, Model model) {
        //这些信息都需要传递到前端做分页
        blogService.deleteBlog(id);  //注意这里需要先删除后再去做分页处理
        PageInfo<Blog> pageInfo = blogService.findPage(pageNum, 8);
        model.addAttribute("pageNum", pageInfo.getPageNum());//得到当前页
        model.addAttribute("pages", pageInfo.getPages()); //得到总页数
        model.addAttribute("list", pageInfo.getList()); //将分页得到的list集合返回到前端
        System.out.println(pageInfo.getList());
        return "manageBlog";
    }

    //模糊查询 主页面搜索功能，得到title含有搜索信息的blog list
    @GetMapping("/getBlogBySearch")
    public String getBlogBySearch(String searchMessage, Model model) {
        if (searchMessage == null || searchMessage.length() == 0) { //如果为空，重新进行/homePage请求
            return "redirect:/homePage";
        }
        List<Blog> blogBySearch = blogService.getBlogBySearch(searchMessage);
        if (blogBySearch == null || blogBySearch.size() == 0) {
            model.addAttribute("msg", "对不起，查无此纪录！/(ㄒoㄒ)/~~"); //将分页得到的list集合返回到前端
        }
        model.addAttribute("list", blogBySearch); //将分页得到的list集合返回到前端
        //返回查询到的数据以及页面的初始化数据
        model.addAttribute("blogBySearch", blogBySearch); //查询到的数据

        PageInfo<Blog> pageInfoModel = blogService.findPageOrderByViews(1, 4); //页面动态首图
        model.addAttribute("pageNum", 1);//得到当前页
        model.addAttribute("pages", 1); //得到总页数  此时不做分页处理
        model.addAttribute("listModel", pageInfoModel.getList()); //将分页得到的list集合返回到前端odel 首图list
        model.addAttribute("total", pageInfoModel.getTotal()); //得到总记录数
        model.addAttribute("views", blogService.getAllViews()); //得到总浏览次数
        model.addAttribute("flags", blogService.getEveryFlagCounts());//得到每个标签的博文的数目
        return "homePage";
    }

    //更新博客
    @GetMapping("/updateBlog")
    public String UpdateBlog(Blog blog) {
        blog.setUpdateTime(new Date()); //创建更新时间
        blogService.updateBlog(blog);
        return "redirect:/homePage";
    }

    //通过标签得到该标签的博客集合  并且返回到原页面
    @GetMapping("/getBlogListByFlag/{flag}")
    public String getBlogListByFlag(@PathVariable("flag") String flag, Model model) {
        List<Blog> blogListByFlag = blogService.getBlogListByFlag(flag);
        PageInfo<Blog> pageInfoModel = blogService.findPageOrderByViews(1, 4); //页面动态首图

        model.addAttribute("list", blogListByFlag);
        model.addAttribute("pageNum", 1);//得到当前页
        model.addAttribute("pages", 1); //得到总页数  此时不做分页处理
        model.addAttribute("listModel", pageInfoModel.getList()); //将分页得到的list集合返回到前端odel 首图list
        model.addAttribute("total", pageInfoModel.getTotal()); //得到总记录数
        model.addAttribute("views", blogService.getAllViews()); //得到总浏览次数
        model.addAttribute("flags", blogService.getEveryFlagCounts());//得到每个标签的博文的数目
        return "homePage";
    }

    //分页  IPviews
    @GetMapping("/getIPviewsByPage/{page}")
    public String getIPviewsByPage(@PathVariable Integer page, Model model) {
        PageInfo<Blog> pageInfo = ipService.IPAccessPageInfo(page, 12);
        model.addAttribute("AccessIP", pageInfo.getList());
        model.addAttribute("pageNum", pageInfo.getPageNum());//得到当前页
        model.addAttribute("pages", pageInfo.getPages()); //得到总页数
        return "IPviews";
    }

    //根究IP来得到搜索结果
    @GetMapping("/searchAccessByIP")
    public String searchAccessByIP(String searchMessage, Model model) {
        if (searchMessage == null || searchMessage.length() == 0) {
            return "redirect:/toIPviews";
        }
        List<AccessIP> accessIPS = ipService.searchAccessByIP(searchMessage);
        if (accessIPS == null || accessIPS.size() == 0) {
            model.addAttribute("msg", "对不起，查无此纪录！/(ㄒoㄒ)/~~");
            PageInfo<Blog> pageInfo = ipService.IPAccessPageInfo(1, 12);
            model.addAttribute("AccessIP", pageInfo.getList());
            model.addAttribute("pageNum", pageInfo.getPageNum());//得到当前页
            model.addAttribute("pages", pageInfo.getPages()); //得到总页数
            return "IPviews";
        } else {
            model.addAttribute("AccessIP", accessIPS);
            model.addAttribute("hiddenPage", "notNull");
            return "IPviews";
        }

    }

    //处理登录请求
    @GetMapping("/login")
    public String login(String username, String password,Model model) {
        //shiro编写认证操作
        //1.获取subject
        Subject subject= SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);

        //3.执行登录方法
        try {
            subject.login(token);  //登录逻辑交给UserRealm
            return "redirect:/homePage";  //登陆成功跳转掉index页面
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名不存在");
            model.addAttribute("username",username);
            model.addAttribute("password", password);
            return "login";
        } catch (IncorrectCredentialsException e){
            model.addAttribute("msg", "密码错误");
            model.addAttribute("username",username);
            return "login";
        }
    }
}