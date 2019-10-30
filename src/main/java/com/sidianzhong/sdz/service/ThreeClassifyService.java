package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
* Created by hxgqh on 2016/1/7.
*/
public interface ThreeClassifyService {

    ThreeClassify create(ThreeClassify item);

    int delete(Integer id);

    int update(ThreeClassify item);

    ThreeClassify get(Integer id);

    PageInfo<ThreeClassify> getListWithPaging(Integer pageNum, Integer pageSize,
                                              String sortItem, String sortOrder, ThreeClassify item);

    //根据parentId查询全部
    List<ThreeClassify> getListByParentId(List<Integer> list);

    //根据Id查询全部
    List<ThreeClassify> getListById(List<Integer> list);
}
