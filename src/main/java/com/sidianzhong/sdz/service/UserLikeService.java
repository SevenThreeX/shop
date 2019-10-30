package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

import java.util.List;

/**
* Created by hxgqh on 2016/1/7.
*/
public interface UserLikeService {

    UserLike create(UserLike item);

    int delete(Integer id);

    int update(UserLike item);

    UserLike get(Integer id);

    PageInfo<UserLike> getListWithPaging(Integer pageNum, Integer pageSize,
                                         String sortItem, String sortOrder, UserLike item);

    //根据评论ID获取点赞次数
    Integer getCountByUserId(Integer commentId);
}
