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
public class UserLikeServiceImpl implements UserLikeService {
    protected static final Logger LOG = LoggerFactory.getLogger(UserLikeServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private UserLikeMapper userLikeMapper;


    @Override
    public UserLike create(UserLike item) {

        userLikeMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return userLikeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(UserLike item) {

        return userLikeMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public UserLike get(Integer id) {
        return userLikeMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<UserLike> getListWithPaging(Integer pageNum, Integer pageSize,
                                                String sortItem, String sortOrder, UserLike item) {

        UserLikeExample example = new UserLikeExample();
        UserLikeExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getCommentId() != null) {
            criteria.andCommentIdEqualTo(item.getCommentId());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
        criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<UserLike> list = this.userLikeMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public Integer getCountByUserId(Integer commentId) {
        UserLikeExample example = new UserLikeExample();
        UserLikeExample.Criteria criteria = example.createCriteria();
        if (commentId != null) {
            criteria.andCommentIdEqualTo(commentId);
        }
        return this.userLikeMapper.selectByExample(example).size();
    }

}
