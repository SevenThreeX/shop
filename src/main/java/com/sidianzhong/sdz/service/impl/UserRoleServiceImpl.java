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
public class UserRoleServiceImpl implements UserRoleService {
    protected static final Logger LOG = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public UserRole create(UserRole item) {

        userRoleMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return userRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(UserRole item) {

        return userRoleMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public UserRole get(Integer id) {
        return userRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<UserRole> getListWithPaging(Integer pageNum, Integer pageSize,
                                                String sortItem, String sortOrder, UserRole item) {

        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getRoleId() != null) {
            criteria.andRoleIdEqualTo(item.getRoleId());
        }
        if (item.getProfile() != null) {
            criteria.andProfileEqualTo(item.getProfile());
        }
//            criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<UserRole> list = this.userRoleMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public List<UserRole> getUserByUserId(Integer userId) {
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        return this.userRoleMapper.selectByExample(example);
    }

    @Override
    public int deleteByUserId(Integer userId) {
        return userRoleMapper.deleteByUserId(userId);
    }
}
