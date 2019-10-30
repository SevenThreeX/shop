package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
* Created by hxgqh on 2016/1/7.
*/
public interface ClassifyService {

    Classify create(Classify item);

    int delete(Integer id);

    int update(Classify item);

    Classify get(Integer id);

    PageInfo<Classify> getListWithPaging(Integer pageNum, Integer pageSize,
                                         String sortItem, String sortOrder, Classify item);

    //获取所有
    List<Classify> getAll();
}
