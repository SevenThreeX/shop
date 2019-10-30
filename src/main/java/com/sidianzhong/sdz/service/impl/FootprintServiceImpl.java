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
public class FootprintServiceImpl implements FootprintService {
    protected static final Logger LOG = LoggerFactory.getLogger(FootprintServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private FootprintMapper footprintMapper;


    @Override
    public Footprint create(Footprint item) {

        footprintMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return footprintMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Footprint item) {

        return footprintMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public Footprint get(Integer id) {
        return footprintMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Footprint> getListWithPaging(Integer pageNum, Integer pageSize,
                                                 String sortItem, String sortOrder, Footprint item) {

        FootprintExample example = new FootprintExample();
        FootprintExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getCommodityId() != null) {
            criteria.andCommodityIdEqualTo(item.getCommodityId());
        }
        criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<Footprint> list = this.footprintMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;
    }

    @Override
    public int deleteByUserId(Integer userId) {
        return footprintMapper.deleteByUserId(userId);
    }

    @Override
    public int getCount(Footprint item) {
        FootprintExample example = new FootprintExample();
        FootprintExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getCommodityId() != null) {
            criteria.andCommodityIdEqualTo(item.getCommodityId());
        }
        return (int) this.footprintMapper.countByExample(example);
    }
}
