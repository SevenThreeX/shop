package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

/**
 * Created by hxgqh on 2016/1/7.
 */
public interface FootprintService {

    Footprint create(Footprint item);

    int delete(Integer id);

    int update(Footprint item);

    Footprint get(Integer id);

    PageInfo<Footprint> getListWithPaging(Integer pageNum, Integer pageSize,
                                          String sortItem, String sortOrder, Footprint item);

    //根据userId删除
    int deleteByUserId(Integer userId);

    int getCount(Footprint item);
}
