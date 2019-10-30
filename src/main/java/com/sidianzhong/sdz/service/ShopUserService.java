package com.sidianzhong.sdz.service;


import com.sidianzhong.sdz.model.ShopUser;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
 * Created by hxgqh on 2016/1/7.
 */
public interface ShopUserService {

    ShopUser create(ShopUser item);

    int delete(Integer id);

    int update(ShopUser item);

    ShopUser get(Integer id);

    PageInfo<ShopUser> getListWithPaging(Integer pageNum, Integer pageSize,
                                         String sortItem, String sortOrder, ShopUser item);

    //根据账号获取用户
    ShopUser getUserByPhone(String phone);

    //根据第三方平台账号获取用户
    ShopUser getUserByWeChat(String weChat);

    //根据userId账号获取用户
    List<ShopUser> getUserById(List<Integer> list);
}
