package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

//bean被容器管理：1，在这个包下 2，有注解
@Repository("alphaHibernate") //访问数据库的注解//给这个bean自定义名字
//仅仅是如下这样，容器不能扫描和装配它，bean不能被容器管理
public class AlphaDaoHibernateImpl implements AlphaDao {
    @Override
    public String select() {
        return "Hibernate";
    }
}
