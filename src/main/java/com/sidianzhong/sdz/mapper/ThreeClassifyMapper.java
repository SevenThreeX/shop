package com.sidianzhong.sdz.mapper;

import com.sidianzhong.sdz.model.ThreeClassify;
import com.sidianzhong.sdz.model.ThreeClassifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThreeClassifyMapper {
    long countByExample(ThreeClassifyExample example);

    int deleteByExample(ThreeClassifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ThreeClassify record);

    int insertSelective(ThreeClassify record);

    List<ThreeClassify> selectByExample(ThreeClassifyExample example);

    ThreeClassify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ThreeClassify record, @Param("example") ThreeClassifyExample example);

    int updateByExample(@Param("record") ThreeClassify record, @Param("example") ThreeClassifyExample example);

    int updateByPrimaryKeySelective(ThreeClassify record);

    int updateByPrimaryKey(ThreeClassify record);
}