package com.sidianzhong.sdz.mapper;

import com.sidianzhong.sdz.model.Classify;
import com.sidianzhong.sdz.model.ClassifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClassifyMapper {
    long countByExample(ClassifyExample example);

    int deleteByExample(ClassifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Classify record);

    int insertSelective(Classify record);

    List<Classify> selectByExample(ClassifyExample example);

    Classify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Classify record, @Param("example") ClassifyExample example);

    int updateByExample(@Param("record") Classify record, @Param("example") ClassifyExample example);

    int updateByPrimaryKeySelective(Classify record);

    int updateByPrimaryKey(Classify record);
}