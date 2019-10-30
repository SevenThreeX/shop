package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
* Created by hxgqh on 2016/1/7.
*/
public interface OneClassifyService {

    OneClassify create(OneClassify item);

    int delete(Integer id);

    int update(OneClassify item);

    OneClassify get(Integer id);

    PageInfo<OneClassify> getListWithPaging(Integer pageNum, Integer pageSize,
                                            String sortItem, String sortOrder, OneClassify item);

    //根据parentId查询列表
    List<OneClassify> getListByParentId(Integer parentId);
}
