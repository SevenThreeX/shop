package com.sidianzhong.sdz.mapper;


import java.util.List;

import com.sidianzhong.sdz.model.ShopUser;
import com.sidianzhong.sdz.model.ShopUserExample;
import org.apache.ibatis.annotations.Param;

public interface ShopUserMapper {
    long countByExample(ShopUserExample example);

    int deleteByExample(ShopUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShopUser record);

    int insertSelective(ShopUser record);

    List<ShopUser> selectByExample(ShopUserExample example);

    ShopUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShopUser record, @Param("example") ShopUserExample example);

    int updateByExample(@Param("record") ShopUser record, @Param("example") ShopUserExample example);

    int updateByPrimaryKeySelective(ShopUser record);

    int updateByPrimaryKey(ShopUser record);

    ShopUser selectUserByPhone(String phone);

    ShopUser selectUserByWeChat(String weChat);

}