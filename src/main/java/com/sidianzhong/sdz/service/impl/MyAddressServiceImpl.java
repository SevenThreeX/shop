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
public class MyAddressServiceImpl implements MyAddressService {
    protected static final Logger LOG = LoggerFactory.getLogger(MyAddressServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private MyAddressMapper myAddressMapper;


    @Override
    public MyAddress create(MyAddress item) {

        myAddressMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return myAddressMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(MyAddress item) {

        return myAddressMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public MyAddress get(Integer id) {
        return myAddressMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<MyAddress> getListWithPaging(Integer pageNum, Integer pageSize,
                                                 String sortItem, String sortOrder, MyAddress item) {

        MyAddressExample example = new MyAddressExample();
        MyAddressExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getProvince() != null) {
            criteria.andProvinceEqualTo(item.getProvince());
        }
        if (item.getCity() != null) {
            criteria.andCityEqualTo(item.getCity());
        }
        if (item.getCounty() != null) {
            criteria.andCountyEqualTo(item.getCounty());
        }
        if (item.getAddress() != null) {
            criteria.andAddressEqualTo(item.getAddress());
        }
        if (item.getPhone() != null) {
            criteria.andPhoneEqualTo(item.getPhone());
        }
        if (item.getName() != null) {
            criteria.andNameEqualTo(item.getName());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<MyAddress> list = this.myAddressMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public int deleteByUserId(Integer userId) {
        return myAddressMapper.deleteByUserId(userId);
    }

    @Override
    public List<MyAddress> getListByUserId(Integer userId) {
        MyAddressExample example = new MyAddressExample();
        MyAddressExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        return this.myAddressMapper.selectByExample(example);
    }
}
