package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
* Created by hxgqh on 2016/1/7.
*/
public interface TwoClassifyService {

    TwoClassify create(TwoClassify item);

    int delete(Integer id);

    int update(TwoClassify item);

    TwoClassify get(Integer id);

    PageInfo<TwoClassify> getListWithPaging(Integer pageNum, Integer pageSize,
                                            String sortItem, String sortOrder, TwoClassify item);

    //根据parentId查询
    List<TwoClassify> getListByParentId(Integer parentId);

    //根据Id查询
    List<TwoClassify> getListById(List<Integer> list);
}
