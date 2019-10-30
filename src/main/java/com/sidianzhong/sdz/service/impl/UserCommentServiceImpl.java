package com.sidianzhong.sdz.service.impl;

import com.github.pagehelper.PageHelper;
import com.sidianzhong.sdz.mapper.*;
import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.service.*;
import com.sidianzhong.sdz.utils.PageInfo;
import com.sidianzhong.sdz.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by hxgqh on 2016/1/7.
 */
@Service
@Transactional
public class UserCommentServiceImpl implements UserCommentService {
    protected static final Logger LOG = LoggerFactory.getLogger(UserCommentServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private UserCommentMapper userCommentMapper;


    @Override
    public UserComment create(UserComment item) {

        userCommentMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return userCommentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(UserComment item) {

        return userCommentMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public UserComment get(Integer id) {
        return userCommentMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<UserComment> getListWithPaging(Integer pageNum, Integer pageSize,
                                                   String sortItem, String sortOrder, UserComment item) {

        UserCommentExample example = new UserCommentExample();
        UserCommentExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getCommodityId() != null) {
            criteria.andCommodityIdEqualTo(item.getCommodityId());
        }
        if (item.getComment() != null) {
            criteria.andCommentEqualTo(item.getComment());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<UserComment> list = this.userCommentMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public List<UserComment> getListByStatus(Integer commodityId, Integer status) {
        UserCommentExample example = new UserCommentExample();
        UserCommentExample.Criteria criteria = example.createCriteria();
        criteria.andCommodityIdEqualTo(commodityId);
        criteria.andStatusEqualTo(status);
        return this.userCommentMapper.selectByExample(example);
    }

}
