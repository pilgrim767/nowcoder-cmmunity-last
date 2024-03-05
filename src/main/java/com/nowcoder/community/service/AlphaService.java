package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service //业务主键加这个注解
//@Scope("prototype") //加上这个prototype之后，每一次getbean都会产生一个实例化一个//但是通常都是单例的
public class AlphaService {

    @Autowired //service 注入dao
    private AlphaDao alphaDao;


    public AlphaService(){
        System.out.println("实例化AlphaService");
    }

    //给bean增加初始化方法
    @PostConstruct //表示容器会在构造器之后调用
    public void init(){
        System.out.println("初始化AlphaService");
    }

    //销毁方法
    @PreDestroy //在销毁对象之前调用它，释放某些资源
    public void destroy(){
        System.out.println("销毁AlphaService");
    }

    //模拟实现查询的业务
    public String find(){
        return alphaDao.select();
    }
}
