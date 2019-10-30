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
public class ShoppingCartServiceImpl implements ShoppingCartService {
    protected static final Logger LOG = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    @Override
    public ShoppingCart create(ShoppingCart item) {

        shoppingCartMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return shoppingCartMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(ShoppingCart item) {

        return shoppingCartMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public ShoppingCart get(Integer id) {
        return shoppingCartMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<ShoppingCart> getListWithPaging(Integer pageNum, Integer pageSize,
                                                    String sortItem, String sortOrder, ShoppingCart item) {

        ShoppingCartExample example = new ShoppingCartExample();
        ShoppingCartExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getCommodityId() != null) {
            criteria.andCommodityIdEqualTo(item.getCommodityId());
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
        criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<ShoppingCart> list = this.shoppingCartMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public int deleteByUserId(Integer userId) {
        return shoppingCartMapper.deleteByUserId(userId);
    }
}
