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
public class ShopUserServiceImpl implements ShopUserService {
    protected static final Logger LOG = LoggerFactory.getLogger(ShopUserServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private ShopUserMapper shopUserMapper;


    @Override
    public ShopUser create(ShopUser item) {
        int insert = shopUserMapper.insert(item);
        if (insert == 0) {
            return null;
        }
        return item;
    }

    @Override
    public int delete(Integer id) {
        return shopUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(ShopUser item) {

        return shopUserMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public ShopUser get(Integer id) {
        return shopUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<ShopUser> getListWithPaging(Integer pageNum, Integer pageSize,
                                                String sortItem, String sortOrder, ShopUser item) {

        ShopUserExample example = new ShopUserExample();
        ShopUserExample.Criteria criteria = example.createCriteria();
        if (item.getName() != null) {
            criteria.andNameEqualTo(item.getName());
        }
        if (item.getPassword() != null) {
            criteria.andPasswordEqualTo(item.getPassword());
        }
        if (item.getPhone() != null) {
            criteria.andPhoneEqualTo(item.getPhone());
        }
        if (item.getPortrait() != null) {
            criteria.andPortraitEqualTo(item.getPortrait());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
        if (item.getWechat() != null) {
            criteria.andWechatEqualTo(item.getWechat());
        }
//            criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<ShopUser> list = this.shopUserMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public ShopUser getUserByPhone(String phone) {
        ShopUser shopUser = this.shopUserMapper.selectUserByPhone(phone);
        return shopUser;
    }

    @Override
    public ShopUser getUserByWeChat(String weChat) {
        ShopUser shopUser = this.shopUserMapper.selectUserByWeChat(weChat);
        return shopUser;
    }

    @Override
    public List<ShopUser> getUserById(List<Integer> list) {
        ShopUserExample example = new ShopUserExample();
        ShopUserExample.Criteria criteria = example.createCriteria();
        if (list != null) {
            criteria.andIdIn(list);
        }
        return this.shopUserMapper.selectByExample(example);
    }

}
