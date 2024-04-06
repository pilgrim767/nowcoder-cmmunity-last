package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
@Deprecated
public interface LoginTicketMapper {

    //insert注解，@Insert({"","",""}) 将 “”，“”多个字符串拼成一个sql语句
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    //声明sql相关的机制：希望主键自动生成，注入给bean的id
    @Options(useGeneratedKeys = true, keyProperty = "id")
    //插入一条数据
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    //查询用户
    LoginTicket selectByTicket(String ticket);

    //<script>里面的脚本和xml写sql语句一样
    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ",
            "</if>",
            "</script>"
    })
    //修改状态
    int updateStatus(String ticket, int status);

}

