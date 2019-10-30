package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

/**
 * Created by hxgqh on 2016/1/7.
 */
public interface MyCollectionService {

    MyCollection create(MyCollection item);

    int delete(Integer id);

    int update(MyCollection item);

    MyCollection get(Integer id);

    PageInfo<MyCollection> getListWithPaging(Integer pageNum, Integer pageSize,
                                             String sortItem, String sortOrder, MyCollection item);

    //根据userId删除
    int deleteByUserId(Integer userId);

    int getCount(MyCollection item);
}
