package com.hncu.cocoblog.shiro;

import com.hncu.cocoblog.entities.Blog;
import com.hncu.cocoblog.entities.User;
import com.hncu.cocoblog.service.blogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cocowwy
 * @create 2020-05-05-23:06
 */
public class UserRealm extends AuthorizingRealm {
    //注入
    @Autowired
    blogService blogService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //给资源进行授权
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        //认证的时候传过来的user
        User user = (User) subject.getPrincipal();
        info.addStringPermission(user.getPerms());

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = blogService.getUserByUsername(token.getUsername());
        //1.判断用户名
        //用户名不存在
        if (user == null) {
            return null; //  底层会抛出UnknowAccountException
        }

        //判断密码
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}