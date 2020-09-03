package com.hncu.cocoblog.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Cocowwy
 * @create 2020-05-05-23:02
 */
@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        /**
         *  anon：无需认证（登录）就可以访问
         *  authc:必须认证了才能访问
         *  user：必须拥有 记住我（remeber me） 才能够直接访问
         *  perms：拥有对某个资源的权限才能访问
         *  role：拥有某个角色权限才能访问
         */

        //添加Shiro的内置过滤器，可以实现权限相关的拦截
        Map<String, String> filterMap = new LinkedHashMap<String, String>();

        //用户登录验证上的拦截
        //左边是拦截的资源的路径   支持通配符 /**  授权过滤器

        filterMap.put("/toLogin", "anon"); //访问登陆页面的请求
        filterMap.put("/login", "anon"); //发送用户信息的请求
        filterMap.put("/showBlog/*", "anon"); //开放查看博客的权限
        filterMap.put("/getBlogListByFlag/*", "anon"); //开放标签查询博客列表的权限
        filterMap.put("/homePage", "anon");//开放查看博客首页的权限
        filterMap.put("/getBlogByPageOrderByViews/*", "anon");//开放分页查询的权限
        filterMap.put("/getBlogBySearch", "anon");//开放搜索的功能


        filterMap.put("/bootstrap/**","anon");
        filterMap.put("/markdown/**","anon");
        filterMap.put("/MyStyle/**","anon");

        filterMap.put("/**", "perms[user:root]"); //配置其他页面拥有root身份 被拦截到了404  跳转到login.jsp

        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/toLogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }

    /**
     * 配置shiroDialect，用于thymeleaf和shiro标签配合使用
     */
    @Bean()
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

}
