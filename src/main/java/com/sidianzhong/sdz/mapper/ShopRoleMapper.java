package com.sidianzhong.sdz.mapper;


import com.sidianzhong.sdz.model.ShopRole;
import com.sidianzhong.sdz.model.ShopRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopRoleMapper {
    long countByExample(ShopRoleExample example);

    int deleteByExample(ShopRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShopRole record);

    int insertSelective(ShopRole record);

    List<ShopRole> selectByExample(ShopRoleExample example);

    ShopRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShopRole record, @Param("example") ShopRoleExample example);

    int updateByExample(@Param("record") ShopRole record, @Param("example") ShopRoleExample example);

    int updateByPrimaryKeySelective(ShopRole record);

    int updateByPrimaryKey(ShopRole record);
}