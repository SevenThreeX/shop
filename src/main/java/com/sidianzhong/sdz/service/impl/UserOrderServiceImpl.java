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
public class UserOrderServiceImpl implements UserOrderService {
    protected static final Logger LOG = LoggerFactory.getLogger(UserOrderServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private UserOrderMapper userOrderMapper;


    @Override
    public UserOrder create(UserOrder item) {

        userOrderMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return userOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(UserOrder item) {

        return userOrderMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public UserOrder get(Integer id) {
        return userOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<UserOrder> getListWithPaging(Integer pageNum, Integer pageSize,
                                                 String sortItem, String sortOrder, UserOrder item) {

        UserOrderExample example = new UserOrderExample();
        UserOrderExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getCommodityId() != null) {
            criteria.andCommodityIdEqualTo(item.getCommodityId());
        }
        if (item.getAddressId() != null) {
            criteria.andAddressIdEqualTo(item.getAddressId());
        }
        if (item.getPrice() != null) {
            criteria.andPriceEqualTo(item.getPrice());
        }
        if (item.getOrderNum() != null) {
            criteria.andOrderNumEqualTo(item.getOrderNum());
        }
        if (item.getPayNum() != null) {
            criteria.andPayNumEqualTo(item.getPayNum());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<UserOrder> list = this.userOrderMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public int deleteByUserId(Integer userId) {
        return userOrderMapper.deleteByUserId(userId);
    }

    @Override
    public UserOrder getOrderByOrderCode(String orderCode) {
        UserOrderExample example = new UserOrderExample();
        UserOrderExample.Criteria criteria = example.createCriteria();
        if (orderCode != null) {
            criteria.andOrderNumEqualTo(orderCode);
        }
        criteria.andLastUpdateTimeEqualTo(new Date());
        return null;
    }

}
