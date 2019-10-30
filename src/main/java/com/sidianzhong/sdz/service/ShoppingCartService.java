package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

/**
 * Created by hxgqh on 2016/1/7.
 */
public interface ShoppingCartService {

    ShoppingCart create(ShoppingCart item);

    int delete(Integer id);

    int update(ShoppingCart item);

    ShoppingCart get(Integer id);

    PageInfo<ShoppingCart> getListWithPaging(Integer pageNum, Integer pageSize,
                                             String sortItem, String sortOrder, ShoppingCart item);

    //根据userId删除
    int deleteByUserId(Integer userId);
}
