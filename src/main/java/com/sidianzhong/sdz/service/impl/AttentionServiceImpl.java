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
public class AttentionServiceImpl implements AttentionService {
    protected static final Logger LOG = LoggerFactory.getLogger(AttentionServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private AttentionMapper attentionMapper;


    @Override
    public Attention create(Attention item) {

        attentionMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return attentionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Attention item) {

        return attentionMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public Attention get(Integer id) {
        return attentionMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Attention> getListWithPaging(Integer pageNum, Integer pageSize,
                                                 String sortItem, String sortOrder, Attention item) {

        AttentionExample example = new AttentionExample();
        AttentionExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getAttentionId() != null) {
            criteria.andAttentionIdEqualTo(item.getAttentionId());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
//            criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<Attention> list = this.attentionMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public int deleteByUserId(Integer userId) {
        return attentionMapper.deleteByUserId(userId);
    }

    @Override
    public int getCount(Attention item) {
        AttentionExample example = new AttentionExample();
        AttentionExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getAttentionId() != null) {
            criteria.andAttentionIdEqualTo(item.getAttentionId());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
        return (int) attentionMapper.countByExample(example);
    }

}
