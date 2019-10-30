package com.sidianzhong.sdz.mapper;

import com.sidianzhong.sdz.model.TwoClassify;
import com.sidianzhong.sdz.model.TwoClassifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TwoClassifyMapper {
    long countByExample(TwoClassifyExample example);

    int deleteByExample(TwoClassifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TwoClassify record);

    int insertSelective(TwoClassify record);

    List<TwoClassify> selectByExample(TwoClassifyExample example);

    TwoClassify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TwoClassify record, @Param("example") TwoClassifyExample example);

    int updateByExample(@Param("record") TwoClassify record, @Param("example") TwoClassifyExample example);

    int updateByPrimaryKeySelective(TwoClassify record);

    int updateByPrimaryKey(TwoClassify record);
}