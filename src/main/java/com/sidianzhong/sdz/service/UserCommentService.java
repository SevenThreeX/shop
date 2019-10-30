package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;
import io.swagger.models.auth.In;

import java.util.List;

/**
* Created by hxgqh on 2016/1/7.
*/
public interface UserCommentService {

    UserComment create(UserComment item);

    int delete(Integer id);

    int update(UserComment item);

    UserComment get(Integer id);

    PageInfo<UserComment> getListWithPaging(Integer pageNum, Integer pageSize,
                                            String sortItem, String sortOrder, UserComment item);

    List<UserComment> getListByStatus(Integer commodityId , Integer status);
}
