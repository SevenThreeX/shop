package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

/**
 * Created by hxgqh on 2016/1/7.
 */
public interface IdentityService {

    Identity create(Identity item);

    int delete(Integer id);

    int update(Identity item);

    Identity get(Integer id);

    PageInfo<Identity> getListWithPaging(Integer pageNum, Integer pageSize,
                                         String sortItem, String sortOrder, Identity item);

    //根据userId删除
    int deleteByUserId(Integer userId);
}
