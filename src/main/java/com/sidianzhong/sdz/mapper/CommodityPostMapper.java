package com.sidianzhong.sdz.mapper;

import com.sidianzhong.sdz.model.CommodityPost;
import com.sidianzhong.sdz.model.CommodityPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommodityPostMapper {
    long countByExample(CommodityPostExample example);

    int deleteByExample(CommodityPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommodityPost record);

    int insertSelective(CommodityPost record);

    List<CommodityPost> selectByExample(CommodityPostExample example);

    CommodityPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommodityPost record, @Param("example") CommodityPostExample example);

    int updateByExample(@Param("record") CommodityPost record, @Param("example") CommodityPostExample example);

    int updateByPrimaryKeySelective(CommodityPost record);

    int updateByPrimaryKey(CommodityPost record);
}