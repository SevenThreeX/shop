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
public class ThreeClassifyServiceImpl implements ThreeClassifyService {
    protected static final Logger LOG = LoggerFactory.getLogger(ThreeClassifyServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private ThreeClassifyMapper threeClassifyMapper;


    @Override
    public ThreeClassify create(ThreeClassify item) {

        threeClassifyMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return threeClassifyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(ThreeClassify item) {

        return threeClassifyMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public ThreeClassify get(Integer id) {
        return threeClassifyMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<ThreeClassify> getListWithPaging(Integer pageNum, Integer pageSize,
                                                     String sortItem, String sortOrder, ThreeClassify item) {

        ThreeClassifyExample example = new ThreeClassifyExample();
        ThreeClassifyExample.Criteria criteria = example.createCriteria();
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
        List<ThreeClassify> list = this.threeClassifyMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public List<ThreeClassify> getListByParentId(List<Integer> list) {
        if (list == null || list.size() == 0) return new ArrayList<>();

        ThreeClassifyExample example = new ThreeClassifyExample();
        ThreeClassifyExample.Criteria criteria = example.createCriteria();

            criteria.andParentIdIn(list);

        return this.threeClassifyMapper.selectByExample(example);
    }

    @Override
    public List<ThreeClassify> getListById(List<Integer> list) {
        if (list == null || list.size() == 0) return new ArrayList<>();

        ThreeClassifyExample example = new ThreeClassifyExample();
        ThreeClassifyExample.Criteria criteria = example.createCriteria();

        criteria.andIdIn(list);

        return this.threeClassifyMapper.selectByExample(example);
    }

}
