package com.sidianzhong.sdz.service;


import com.sidianzhong.sdz.model.UserRole;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
* Created by hxgqh on 2016/1/7.
*/
public interface UserRoleService {

    UserRole create(UserRole item);

    int delete(Integer id);

    int update(UserRole item);

    UserRole get(Integer id);

    PageInfo<UserRole> getListWithPaging(Integer pageNum, Integer pageSize,
                                         String sortItem, String sortOrder, UserRole item);

    //根据userId查询
    List<UserRole> getUserByUserId(Integer userId);

    //根据userId删除
    int deleteByUserId(Integer userId);
}
