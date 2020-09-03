package com.hncu.cocoblog.controller;

import com.github.pagehelper.PageInfo;
import com.hncu.cocoblog.entities.AccessIP;
import com.hncu.cocoblog.entities.Blog;
import com.hncu.cocoblog.service.blogService;
import com.hncu.cocoblog.service.ipService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sun.util.calendar.LocalGregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 路由控制器
 *
 * @author Cocowwy
 * @create 2020-05-05-23:56
 */
@Controller
@Transactional(rollbackFor = Exception.class) //设置全局对exception异常的回滚
public class Routecontroller {

    @Autowired
    blogService blogService;

    @Autowired
    ipService ipService;

    //跳转到博客首页的界面
    @GetMapping("/homePage")
    public String toHomePage(Model model) {
        //初始化跳转博客的数据
        //分页 初始化第一页的数据
        PageInfo pageInfo = blogService.findPageOrderByViews(1, 8);
        PageInfo<Blog> pageInfoModel = blogService.findPageOrderByViews(1, 4); //页面动态首图
        model.addAttribute("pageNum", pageInfo.getPageNum());//得到当前页
        model.addAttribute("pages", pageInfo.getPages()); //得到总页数
        model.addAttribute("list", pageInfo.getList()); //将分页得到的list集合返回到前端
        model.addAttribute("listModel", pageInfoModel.getList()); //将分页得到的list集合返回到前端
        model.addAttribute("total", blogService.getBlogCount()); //得到总记录数
        model.addAttribute("views", ipService.getAllViews()); //得到总浏览次数
        model.addAttribute("flags", blogService.getEveryFlagCounts());//得到每个标签的博文的数目

        return "homePage";
    }

    //跳转到编辑博客的页面
    @GetMapping("/editBlog")
    public String toEditBlog() {
        return "editBlog";
    }

    //跳转到管理博客的页面
    @GetMapping("/manageBlog")
    public String toManageBlog(Model model) {
        //第一次页面跳转的时候需要得到第一页的数据  初始化管理博客的数据
        PageInfo<Blog> pageInfo = blogService.findPage(1, 8);
        model.addAttribute("pageNum", pageInfo.getPageNum());//得到当前页
        model.addAttribute("pages", pageInfo.getPages()); //得到总页数
        model.addAttribute("list", pageInfo.getList()); //将分页得到的list集合返回到前端
        return "manageBlog";
    }

    //跳转到博客展示页
    @GetMapping("/showBlog/{id}")
    public String toShowBlog(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
        Blog blogById = blogService.getBlogById(id);

        String ip = request.getRemoteAddr();//得到当前访问的IP
        //先判断该ip是否访问过该博客
        AccessIP accessResult = ipService.getAccessWithIP(ip, id);
        if (accessResult == null) {
            //说明无记录 那么就保存一个新的记录进去 初始化一个新的记录数据
            //对ip表进行记录
            AccessIP accessIP = new AccessIP();
            accessIP.setIp(ip);
            accessIP.setViews(1);
            accessIP.setBlogId(id);
            accessIP.setLastAccess(new Date());
            ipService.saveAccessWithIP(accessIP);
            //此时还需要对t_blog的表的数据进行跟新 使该view+1
            blogService.addViewById(id);
        } else {
            //如果不为空 先判断两次记录的时间差 如果大于10分钟则加1 那么根据查到的记录的值进行加1
            if (new Date().getTime() - accessResult.getLastAccess().getTime() >= 1000 * 60 * 10) {
                ipService.updateThisIPViews(accessResult.getId(), new Date()); //更新最后一次的时间
                blogService.addViewById(id);
            }
        }

        model.addAttribute("blog", blogById);
        model.addAttribute("total", blogService.getBlogCount());//得到总记录数
        model.addAttribute("views", ipService.getAllViews()); //得到总浏览次数
        return "showBlog";
    }

    //跳转到博客的重新编辑页面
    @GetMapping("/toReeditBlog/{id}")
    public String toReeditBlog(@PathVariable("id") Integer id, Model model) {
        Blog blogById = blogService.getBlogById(id);//得到需要编辑的blog
        model.addAttribute("blog", blogById); //返回数据
        return "reeditBlog";
    }

    //跳转到IP浏览统计页面
    @GetMapping("/toIPviews")
    public String toIPviews(Model model) {
//        List<AccessIP> accessIPListWithBlogTitle = ipService.getAccessIPListWithBlogTitle();
//        model.addAttribute("AccessIP", accessIPListWithBlogTitle);
        //分页 初始化传第一页的前12份数据
        PageInfo<AccessIP> pageInfo = ipService.IPAccessPageInfo(1, 12);
        model.addAttribute("AccessIP", pageInfo.getList());
        model.addAttribute("pageNum", pageInfo.getPageNum());//得到当前页
        model.addAttribute("pages", pageInfo.getPages()); //得到总页数
        return "IPviews";
    }

    //跳转到登录页面
    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }
}
