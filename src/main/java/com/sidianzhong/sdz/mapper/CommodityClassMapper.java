package com.sidianzhong.sdz.mapper;

import com.sidianzhong.sdz.model.CommodityClass;
import com.sidianzhong.sdz.model.CommodityClassExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CommodityClassMapper {
    long countByExample(CommodityClassExample example);

    int deleteByExample(CommodityClassExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommodityClass record);

    int insertList(@Param("list") List<CommodityClass> list);

    int insertSelective(CommodityClass record);

    List<CommodityClass> selectByExample(CommodityClassExample example);

    CommodityClass selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommodityClass record, @Param("example") CommodityClassExample example);

    int updateByExample(@Param("record") CommodityClass record, @Param("example") CommodityClassExample example);

    int updateByPrimaryKeySelective(CommodityClass record);

    int updateByPrimaryKey(CommodityClass record);
}