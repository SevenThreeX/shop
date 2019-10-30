package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
 * Created by hxgqh on 2016/1/7.
 */
public interface MyAddressService {

    MyAddress create(MyAddress item);

    int delete(Integer id);

    int update(MyAddress item);

    MyAddress get(Integer id);

    PageInfo<MyAddress> getListWithPaging(Integer pageNum, Integer pageSize,
                                          String sortItem, String sortOrder, MyAddress item);

    //根据userId删除
    int deleteByUserId(Integer userId);

    //根据userId查询
    List<MyAddress> getListByUserId(Integer userId);
}
