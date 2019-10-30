package com.sidianzhong.sdz.mapper;

import com.sidianzhong.sdz.model.UserLike;
import com.sidianzhong.sdz.model.UserLikeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserLikeMapper {
    long countByExample(UserLikeExample example);

    int deleteByExample(UserLikeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserLike record);

    int insertSelective(UserLike record);

    List<UserLike> selectByExample(UserLikeExample example);

    UserLike selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserLike record, @Param("example") UserLikeExample example);

    int updateByExample(@Param("record") UserLike record, @Param("example") UserLikeExample example);

    int updateByPrimaryKeySelective(UserLike record);

    int updateByPrimaryKey(UserLike record);
}