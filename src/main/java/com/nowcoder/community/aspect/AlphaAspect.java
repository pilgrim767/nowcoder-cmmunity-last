package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

//@Component
//@Aspect
public class AlphaAspect {

    // 定义要植入代码中的切点
    // 通过注解定义切点
    // *：方法返回值 -- 包名...下的所有方法
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut() {

    }

    // 定义通知
    // 连接点开始做什么
    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    // 连接点之后做什么
    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    // 返回值之后处理
    @AfterReturning("pointcut()")
    public void afterRetuning() {
        System.out.println("afterRetuning");
    }

    // 抛异常的时候植入代码
    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    // 前后都植入逻辑
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }

}
