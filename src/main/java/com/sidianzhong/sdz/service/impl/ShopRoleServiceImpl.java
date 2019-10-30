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
public class ShopRoleServiceImpl implements ShopRoleService{
    protected static final Logger LOG = LoggerFactory.getLogger(ShopRoleServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private ShopRoleMapper shopRoleMapper;


    @Override
    public ShopRole create(ShopRole item) {

         shopRoleMapper.insert(item);
         return item;
    }

    @Override
    public int delete(Integer id) {
        return shopRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(ShopRole item) {

        return shopRoleMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public ShopRole get(Integer id) {
        return shopRoleMapper.selectByPrimaryKey(id);
    }
    @Override
     public PageInfo<ShopRole> getListWithPaging(Integer pageNum, Integer pageSize,
                                                  String sortItem, String sortOrder,ShopRole item){

        ShopRoleExample  example = new ShopRoleExample();
        ShopRoleExample.Criteria criteria = example.createCriteria();
        if(item.getName() != null ){
            criteria.andNameEqualTo(item.getName());
        }


        example.setOrderByClause(Tools.humpToLine(sortItem)+" "+sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List< ShopRole> list = this.shopRoleMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

     }

    @Override
    public List<ShopRole> getListByRoleIds(List<Integer> list) {

        ShopRoleExample  example = new ShopRoleExample();
        ShopRoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(list);
        return this.shopRoleMapper.selectByExample(example);
    }

}
