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
public class MyCollectionServiceImpl implements MyCollectionService {
    protected static final Logger LOG = LoggerFactory.getLogger(MyCollectionServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private MyCollectionMapper myCollectionMapper;


    @Override
    public MyCollection create(MyCollection item) {

        myCollectionMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return myCollectionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(MyCollection item) {

        return myCollectionMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public MyCollection get(Integer id) {
        return myCollectionMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<MyCollection> getListWithPaging(Integer pageNum, Integer pageSize,
                                                    String sortItem, String sortOrder, MyCollection item) {

        MyCollectionExample example = new MyCollectionExample();
        MyCollectionExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getCollectionId() != null) {
            criteria.andCollectionIdEqualTo(item.getCollectionId());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
        criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<MyCollection> list = this.myCollectionMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public int deleteByUserId(Integer userId) {
        return myCollectionMapper.deleteByUserId(userId);
    }

    @Override
    public int getCount(MyCollection item) {
        MyCollectionExample example = new MyCollectionExample();
        MyCollectionExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getCollectionId() != null) {
            criteria.andCollectionIdEqualTo(item.getCollectionId());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
        return (int) this.myCollectionMapper.countByExample(example);
    }
}
