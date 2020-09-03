package com.hncu.cocoblog.aspect;

import com.sun.org.apache.regexp.internal.RE;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.net.util.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 为controller层编写的日志切面类
 *
 * @author Cocowwy
 * @create 2020-05-05-23:02
 */

@Aspect
@Component
public class WebLogAspect {
    public final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.hncu.cocoblog.controller.*.*(..))")
    public void controllerPintcut() {
    }  //用来标记切入点Pointcut

    //配置环绕通知
    @Around("controllerPintcut()")
    public Object logArroundController(ProceedingJoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Object returnValue = null; //业务层调用方法的返回值
        try {
            Object[] args = joinPoint.getArgs();//得到方法执行所需的参数
            System.out.println("======环绕前置======");
            // 记录下请求内容
            logger.info("请求地址 : " + request.getRequestURL().toString());
            logger.info("HTTP METHOD : " + request.getMethod());
            logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                    + joinPoint.getSignature().getName());
            logger.info("携带参数 : " + Arrays.toString(joinPoint.getArgs()));
            Date start = new Date();
            logger.info("访问开始时间 :" + start.toString());
            returnValue = joinPoint.proceed(args);//明确调用业务层方法（切入点方法）

            System.out.println("======环绕后置通知======");
            Date end = new Date();
            logger.info("访问结束时间 : " + end.toString());
            long useTime = end.getTime() - start.getTime();
            logger.info("用时" + String.valueOf(useTime)+"ms");
            return returnValue;
        } catch (Throwable throwable) {
            System.out.println("======环绕异常通知======");
            logger.info("异常发生时间 : " + new Date().toString());
            logger.info("异常信息 : " + throwable.getMessage());
            throw new RuntimeException(throwable);
        }finally {
            System.out.println("======环绕最终通知======");
        }
    }
}
