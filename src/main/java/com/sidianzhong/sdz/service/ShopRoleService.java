package com.sidianzhong.sdz.service;


import com.sidianzhong.sdz.model.ShopRole;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
* Created by hxgqh on 2016/1/7.
*/
public interface ShopRoleService {

    ShopRole create(ShopRole item);

    int delete(Integer id);

    int update(ShopRole item);

    ShopRole get(Integer id);

    PageInfo<ShopRole> getListWithPaging(Integer pageNum, Integer pageSize,
                                         String sortItem, String sortOrder, ShopRole item);

    List<ShopRole> getListByRoleIds(List<Integer> list);
}
