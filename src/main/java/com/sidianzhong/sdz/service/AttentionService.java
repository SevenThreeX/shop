package com.sidianzhong.sdz.service;

import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.utils.PageInfo;

/**
 * Created by hxgqh on 2016/1/7.
 */
public interface AttentionService {

    Attention create(Attention item);

    int delete(Integer id);

    int update(Attention item);

    Attention get(Integer id);

    PageInfo<Attention> getListWithPaging(Integer pageNum, Integer pageSize,
                                          String sortItem, String sortOrder, Attention item);

    //根据userId删除
    int deleteByUserId(Integer userId);

    //根据用户查询关注数量
    int getCount(Attention item);

}
