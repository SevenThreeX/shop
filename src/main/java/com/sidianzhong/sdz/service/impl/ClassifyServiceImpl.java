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
public class ClassifyServiceImpl implements ClassifyService{
    protected static final Logger LOG = LoggerFactory.getLogger(ClassifyServiceImpl.class);

    // 将所有的modelMapper注入
    @Autowired
    private ClassifyMapper classifyMapper;


    @Override
    public Classify create(Classify item) {

         classifyMapper.insert(item);
         return item;
    }

    @Override
    public int delete(Integer id) {
        return classifyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Classify item) {

        return classifyMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public Classify get(Integer id) {
        return classifyMapper.selectByPrimaryKey(id);
    }
    @Override
     public PageInfo<Classify> getListWithPaging(Integer pageNum, Integer pageSize,
                                                  String sortItem, String sortOrder,Classify item){

        ClassifyExample  example = new ClassifyExample();
        ClassifyExample.Criteria criteria = example.createCriteria();
        if(item.getName() != null ){
            criteria.andNameEqualTo(item.getName());
        }
        if(item.getContent() != null ){
            criteria.andContentEqualTo(item.getContent());
        }
        if(item.getStatus() != null ){
            criteria.andStatusEqualTo(item.getStatus());
        }
//            criteria.andLastUpdateTimeEqualTo(new Date());

        example.setOrderByClause(Tools.humpToLine(sortItem)+" "+sortOrder);
        PageHelper.startPage(pageNum, pageSize);
        List< Classify> list = this.classifyMapper.selectByExample(example);
        PageInfo result = new PageInfo(list);

        return result;

     }

    @Override
    public List<Classify> getAll() {
        ClassifyExample  example = new ClassifyExample();
        return this.classifyMapper.selectByExample(example);
    }


}
