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
public class CommodityClassServiceImpl implements CommodityClassService {
    protected static final Logger LOG = LoggerFactory.getLogger(CommodityClassServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private CommodityClassMapper commodityClassMapper;


    @Override
    public CommodityClass create(CommodityClass item) {
        commodityClassMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return commodityClassMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(CommodityClass item) {
        return commodityClassMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public CommodityClass get(Integer id) {
        return commodityClassMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<CommodityClass> getListWithPaging(Integer pageNum, Integer pageSize,
                                                      String sortItem, String sortOrder, CommodityClass item) {

        CommodityClassExample example = new CommodityClassExample();
        CommodityClassExample.Criteria criteria = example.createCriteria();
        if (item.getCommodityId() != null) {
            criteria.andCommodityIdEqualTo(item.getCommodityId());
        }
        if (item.getClassId() != null) {
            criteria.andClassIdEqualTo(item.getClassId());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
//            criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<CommodityClass> list = this.commodityClassMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public List<CommodityClass> getListByClassId(List<Integer> list) {
        if (list == null || list.size() == 0) return new ArrayList<>();

        CommodityClassExample example = new CommodityClassExample();
        CommodityClassExample.Criteria criteria = example.createCriteria();
        criteria.andClassIdIn(list);
        return this.commodityClassMapper.selectByExample(example);
    }

    @Override
    public List<CommodityClass> getListByCommodityId(List<Integer> list) {
        if (list == null || list.size() == 0) return new ArrayList<>();

        CommodityClassExample example = new CommodityClassExample();
        CommodityClassExample.Criteria criteria = example.createCriteria();
        criteria.andCommodityIdIn(list);
        return this.commodityClassMapper.selectByExample(example);
    }

    @Override
    public int createList(List<CommodityClass> list) {
        return commodityClassMapper.insertList(list);
    }

}
