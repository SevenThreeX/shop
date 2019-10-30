package com.sidianzhong.sdz.mapper;

import com.sidianzhong.sdz.model.MyAddress;
import com.sidianzhong.sdz.model.MyAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MyAddressMapper {
    long countByExample(MyAddressExample example);

    int deleteByExample(MyAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MyAddress record);

    int insertSelective(MyAddress record);

    List<MyAddress> selectByExample(MyAddressExample example);

    MyAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MyAddress record, @Param("example") MyAddressExample example);

    int updateByExample(@Param("record") MyAddress record, @Param("example") MyAddressExample example);

    int updateByPrimaryKeySelective(MyAddress record);

    int updateByPrimaryKey(MyAddress record);

    //根据userId删除
    int deleteByUserId(Integer userId);
}