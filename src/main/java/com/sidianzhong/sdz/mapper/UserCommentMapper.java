package com.sidianzhong.sdz.mapper;

import com.sidianzhong.sdz.model.UserComment;
import com.sidianzhong.sdz.model.UserCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCommentMapper {
    long countByExample(UserCommentExample example);

    int deleteByExample(UserCommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserComment record);

    int insertSelective(UserComment record);

    List<UserComment> selectByExample(UserCommentExample example);

    UserComment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserComment record, @Param("example") UserCommentExample example);

    int updateByExample(@Param("record") UserComment record, @Param("example") UserCommentExample example);

    int updateByPrimaryKeySelective(UserComment record);

    int updateByPrimaryKey(UserComment record);
}