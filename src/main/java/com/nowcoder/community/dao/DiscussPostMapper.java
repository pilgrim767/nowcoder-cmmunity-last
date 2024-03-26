package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    //offset起始行行号 limit每页最多显示行数
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // @Param注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.
    int selectDiscussPostRows(@Param("userId") int userId);

    // 增加帖子的方法
    // 返回增加的行数
    int insertDiscussPost(DiscussPost discussPost);

    // 查询帖子的详细信息【根据帖子id】
    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id, int commentCount);

}

