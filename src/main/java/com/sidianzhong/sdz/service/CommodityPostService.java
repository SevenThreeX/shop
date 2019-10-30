package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
* Created by hxgqh on 2016/1/7.
*/
public interface CommodityPostService {

    CommodityPost create(CommodityPost item);

    int delete(Integer id);

    int update(CommodityPost item);

    CommodityPost get(Integer id);

    PageInfo<CommodityPost> getListWithPaging(Integer pageNum, Integer pageSize,
                                              String sortItem, String sortOrder, CommodityPost item);

    //根据commodityId获取商品
    List<CommodityPost> getPostByCommodityId(Integer commodityId);
}
