package com.sidianzhong.sdz.mapper;

import com.sidianzhong.sdz.model.OneClassify;
import com.sidianzhong.sdz.model.OneClassifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OneClassifyMapper {
    long countByExample(OneClassifyExample example);

    int deleteByExample(OneClassifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OneClassify record);

    int insertSelective(OneClassify record);

    List<OneClassify> selectByExample(OneClassifyExample example);

    OneClassify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OneClassify record, @Param("example") OneClassifyExample example);

    int updateByExample(@Param("record") OneClassify record, @Param("example") OneClassifyExample example);

    int updateByPrimaryKeySelective(OneClassify record);

    int updateByPrimaryKey(OneClassify record);
}