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
public class OneClassifyServiceImpl implements OneClassifyService{
    protected static final Logger LOG = LoggerFactory.getLogger(OneClassifyServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private OneClassifyMapper oneClassifyMapper;


    @Override
    public OneClassify create(OneClassify item) {

         oneClassifyMapper.insert(item);
         return item;
    }

    @Override
    public int delete(Integer id) {
        return oneClassifyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(OneClassify item) {

        return oneClassifyMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public OneClassify get(Integer id) {
        return oneClassifyMapper.selectByPrimaryKey(id);
    }
    @Override
     public PageInfo<OneClassify> getListWithPaging(Integer pageNum, Integer pageSize,
                                                  String sortItem, String sortOrder,OneClassify item){

        OneClassifyExample  example = new OneClassifyExample();
        OneClassifyExample.Criteria criteria = example.createCriteria();
        if(item.getName() != null ){
            criteria.andNameEqualTo(item.getName());
        }
        if(item.getContent() != null ){
            criteria.andContentEqualTo(item.getContent());
        }
        if(item.getParentId() != null ){
            criteria.andParentIdEqualTo(item.getParentId());
        }
        if(item.getStatus() != null ){
            criteria.andStatusEqualTo(item.getStatus());
        }

        example.setOrderByClause(Tools.humpToLine(sortItem)+" "+sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List< OneClassify> list = this.oneClassifyMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

     }

    @Override
    public List<OneClassify> getListByParentId(Integer parentId) {
        OneClassifyExample example = new OneClassifyExample();
        OneClassifyExample.Criteria criteria = example.createCriteria();
        if (parentId != null) {
            criteria.andParentIdEqualTo(parentId);
        }
        return this.oneClassifyMapper.selectByExample(example);
    }
}
