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
public class CommodityServiceImpl implements CommodityService {
    protected static final Logger LOG = LoggerFactory.getLogger(CommodityServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private CommodityMapper commodityMapper;


    @Override
    public int create(Commodity item) {
        return commodityMapper.insert(item);
    }

    @Override
    public int delete(Integer id) {
        return commodityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Commodity item) {

        return commodityMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public Commodity get(Integer id) {
        return commodityMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Commodity> getListWithPaging(Integer pageNum, Integer pageSize,
                                                 String sortItem, String sortOrder, Commodity item) {

        CommodityExample example = new CommodityExample();
        CommodityExample.Criteria criteria = example.createCriteria();
        if (item.getName() != null) {
            criteria.andNameEqualTo(item.getName());
        }
        if (item.getPrice() != null) {
            criteria.andPriceEqualTo(item.getPrice());
        }
        if (item.getImg() != null) {
            criteria.andImgEqualTo(item.getImg());
        }
//        if(item.getDetails() != null ){
//            criteria.andDetailsEqualTo(item.getDetails());
//        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
        criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<Commodity> list = this.commodityMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public List<Commodity> getListByIds(List<Integer> list) {
        if (list == null || list.size() == 0) return new ArrayList<>();
        CommodityExample example = new CommodityExample();
        CommodityExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(list);
        List<Commodity> lists = this.commodityMapper.selectByExample(example);
        return lists;
    }

}
