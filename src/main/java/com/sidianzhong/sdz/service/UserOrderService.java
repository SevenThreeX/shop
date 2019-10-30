package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

/**
 * Created by hxgqh on 2016/1/7.
 */
public interface UserOrderService {

    UserOrder create(UserOrder item);

    int delete(Integer id);

    int update(UserOrder item);

    UserOrder get(Integer id);

    PageInfo<UserOrder> getListWithPaging(Integer pageNum, Integer pageSize,
                                          String sortItem, String sortOrder, UserOrder item);

    //根据userId删除
    int deleteByUserId(Integer userId);

    //根据订单号查询
    UserOrder getOrderByOrderCode(String orderCode);
}
