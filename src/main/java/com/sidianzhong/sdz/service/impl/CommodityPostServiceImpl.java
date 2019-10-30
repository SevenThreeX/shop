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
public class CommodityPostServiceImpl implements CommodityPostService {
    protected static final Logger LOG = LoggerFactory.getLogger(CommodityPostServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private CommodityPostMapper commodityPostMapper;


    @Override
    public CommodityPost create(CommodityPost item) {

        commodityPostMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return commodityPostMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(CommodityPost item) {

        return commodityPostMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public CommodityPost get(Integer id) {
        return commodityPostMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<CommodityPost> getListWithPaging(Integer pageNum, Integer pageSize,
                                                     String sortItem, String sortOrder, CommodityPost item) {

        CommodityPostExample example = new CommodityPostExample();
        CommodityPostExample.Criteria criteria = example.createCriteria();
        if (item.getCommodityId() != null) {
            criteria.andCommodityIdEqualTo(item.getCommodityId());
        }
        if (item.getPost() != null) {
            criteria.andPostEqualTo(item.getPost());
        }
        if (item.getPrice() != null) {
            criteria.andPriceEqualTo(item.getPrice());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
//            criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<CommodityPost> list = this.commodityPostMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public List<CommodityPost> getPostByCommodityId(Integer commodityId) {
        CommodityPostExample example = new CommodityPostExample();
        CommodityPostExample.Criteria criteria = example.createCriteria();
        if (commodityId != null) {
            criteria.andCommodityIdEqualTo(commodityId);
        }
        return this.commodityPostMapper.selectByExample(example);
    }

}
