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
public class IdentityServiceImpl implements IdentityService {
    protected static final Logger LOG = LoggerFactory.getLogger(IdentityServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private IdentityMapper identityMapper;


    @Override
    public Identity create(Identity item) {

        identityMapper.insert(item);
        return item;
    }

    @Override
    public int delete(Integer id) {
        return identityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Identity item) {

        return identityMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public Identity get(Integer id) {
        return identityMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Identity> getListWithPaging(Integer pageNum, Integer pageSize,
                                                String sortItem, String sortOrder, Identity item) {

        IdentityExample example = new IdentityExample();
        IdentityExample.Criteria criteria = example.createCriteria();
        if (item.getUserId() != null) {
            criteria.andUserIdEqualTo(item.getUserId());
        }
        if (item.getName() != null) {
            criteria.andNameEqualTo(item.getName());
        }
        if (item.getCardNum() != null) {
            criteria.andCardNumEqualTo(item.getCardNum());
        }
        if (item.getCardFront() != null) {
            criteria.andCardFrontEqualTo(item.getCardFront());
        }
        if (item.getCardReverse() != null) {
            criteria.andCardReverseEqualTo(item.getCardReverse());
        }
        if (item.getOtherCard() != null) {
            criteria.andOtherCardEqualTo(item.getOtherCard());
        }
        criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem) + " " + sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List<Identity> list = this.identityMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

    }

    @Override
    public int deleteByUserId(Integer userId) {
        return identityMapper.deleteByUserId(userId);
    }
}
