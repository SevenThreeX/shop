package com.sidianzhong.sdz.mapper;


import java.util.List;

import com.sidianzhong.sdz.model.UserRole;
import com.sidianzhong.sdz.model.UserRoleExample;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
    long countByExample(UserRoleExample example);

    int deleteByExample(UserRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> selectByExample(UserRoleExample example);

    UserRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRole record, @Param("example") UserRoleExample example);

    int updateByExample(@Param("record") UserRole record, @Param("example") UserRoleExample example);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    //根据userId查询
    List<UserRole> selectByUserId(Integer userId);

    //根据userId删除
    int deleteByUserId(Integer userId);
}