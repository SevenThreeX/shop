package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
 * Created by hxgqh on 2016/1/7.
 */
public interface CommodityClassService {

    CommodityClass create(CommodityClass item);

    int delete(Integer id);

    int update(CommodityClass item);

    CommodityClass get(Integer id);

    PageInfo<CommodityClass> getListWithPaging(Integer pageNum, Integer pageSize,
                                               String sortItem, String sortOrder, CommodityClass item);

    //根据ClassId查询出商品分类
    List<CommodityClass> getListByClassId(List<Integer> list);

    //根据commodityId查询出商品分类
    List<CommodityClass> getListByCommodityId(List<Integer> list);

    //根据commodityId查询出商品分类
    int createList(List<CommodityClass> list);
}
