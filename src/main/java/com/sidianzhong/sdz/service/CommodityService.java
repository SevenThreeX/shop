package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
* Created by hxgqh on 2016/1/7.
*/
public interface CommodityService {

    int create(Commodity item);

    int delete(Integer id);

    int update(Commodity item);

    Commodity get(Integer id);

    PageInfo<Commodity> getListWithPaging(Integer pageNum, Integer pageSize,
                                          String sortItem, String sortOrder, Commodity item);

    List<Commodity> getListByIds(List<Integer> list);
}
