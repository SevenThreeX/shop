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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hxgqh on 2016/1/7.
 */
@Service
@Transactional
public class TwoClassifyServiceImpl implements TwoClassifyService {
    protected static final Logger LOG = LoggerFactory.getLogger(TwoClassifyServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private TwoClassifyMapper twoClassifyMapper;


    @Override
    public TwoClassify create(TwoClassify item) {

        twoClassifyMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return twoClassifyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(TwoClassify item) {

        return twoClassifyMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public TwoClassify get(Integer id) {
        return twoClassifyMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<TwoClassify> getListWithPaging(Integer pageNum, Integer pageSize,
                                                   String sortItem, String sortOrder, TwoClassify item) {

        TwoClassifyExample example = new TwoClassifyExample();
        TwoClassifyExample.Criteria criteria = example.createCriteria();
        if (item.getName() != null) {
            criteria.andNameEqualTo(item.getName());
        }
        if (item.getContent() != null) {
            criteria.andContentEqualTo(item.getContent());
        }
        if (item.getParentId() != null) {
            criteria.andParentIdEqualTo(item.getParentId());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<TwoClassify> list = this.twoClassifyMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public List<TwoClassify> getListByParentId(Integer parentId) {
        TwoClassifyExample example = new TwoClassifyExample();
        TwoClassifyExample.Criteria criteria = example.createCriteria();
        if (parentId != null) {
            criteria.andParentIdEqualTo(parentId);
        }
        return this.twoClassifyMapper.selectByExample(example);
    }

    @Override
    public List<TwoClassify> getListById(List<Integer> list) {
        TwoClassifyExample example = new TwoClassifyExample();
        TwoClassifyExample.Criteria criteria = example.createCriteria();
        if (list != null) {
            criteria.andIdIn(list);
        }
        return this.twoClassifyMapper.selectByExample(example);
    }
}
