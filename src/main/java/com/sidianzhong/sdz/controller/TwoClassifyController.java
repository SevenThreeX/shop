package com.sidianzhong.sdz.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sidianzhong.sdz.annotation.BackLoginToken;
import com.sidianzhong.sdz.annotation.UserLoginToken;
import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.service.*;
import com.sidianzhong.sdz.utils.PageInfo;
import com.sidianzhong.sdz.utils.ResultModel;
import com.sidianzhong.sdz.utils.ResultStatus;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hxg on 2016/10/06.
 */
@Api(description = "二级分类")
@Controller
public class TwoClassifyController {

    @Autowired
    TwoClassifyService twoClassifyService;
    @Autowired
    ThreeClassifyService threeClassifyService;

    @Autowired
    HttpServletResponse response;

    @BackLoginToken
    @ApiOperation(value = "创建'二级分类'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/two_classifies/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createTwoClassify(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "parentId", required = false) Integer parentId,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime
    ) {
        TwoClassify item = new TwoClassify();
        Date date = new Date();
        if (name != null) {
            item.setName(name);
        }
        if (content != null) {
            item.setContent(content);
        }
        if (parentId != null) {
            item.setParentId(parentId);
        }
        item.setStatus(status == null ? 0 : status);
        item.setCreateTime(date);
        item.setLastUpdateTime(date);
        if (twoClassifyService.create(item) == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @BackLoginToken
    @ApiOperation(value = "删除'二级分类'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/two_classifies/delete",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteTwoClassify(
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "two_classify_id") Integer id
    ) {
        TwoClassify item = twoClassifyService.get(id);
        if (null == item || twoClassifyService.delete(id) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @BackLoginToken
    @ApiOperation(value = "修改'二级分类'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/two_classifies/edit",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editTwoClassify(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "parentId", required = false) Integer parentId,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        TwoClassify item = twoClassifyService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        if (name != null) {
            item.setName(name);
        }
        if (content != null) {
            item.setContent(content);
        }
        if (parentId != null) {
            item.setParentId(parentId);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        if (twoClassifyService.update(item) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "查询'二级分类'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/getTwoClassifyById",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getTwoClassifyById(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "id") Integer id
    ) {

        TwoClassify item = twoClassifyService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResultModel.ok(item), HttpStatus.OK);

    }

    @UserLoginToken
    @ApiOperation(value = "查询'二级分类'表中的多条记录或者新增某条记录", notes = "get传参：根据所需的字段进行匹配查询。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder； ")
    @RequestMapping(value = "/two_classifies",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getTwoClassifies(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize,
            @RequestParam(value = "sortItem", required = false, defaultValue = "id") String sortItem,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "parentId", required = false) Integer parentId,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        TwoClassify item = new TwoClassify();
        if (name != null) {
            item.setName(name);
        }
        if (content != null) {
            item.setContent(content);
        }
        if (parentId != null) {
            item.setParentId(parentId);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        PageInfo<TwoClassify> list = twoClassifyService.getListWithPaging(pageNum, pageSize, sortItem, sortOrder, item);
        return new ResponseEntity<>(ResultModel.ok(list), HttpStatus.OK);
    }


    @ApiOperation(value = "查询分类列表")
    @RequestMapping(value = "/getCommodityClasses",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCommodityClasses(
            @RequestParam(value = "parentId") Integer parentId
    ) {
        //获取出所在的二级列表全部的分类
        List<TwoClassify> twoClassifies = twoClassifyService.getListByParentId(parentId);
        List<Integer> twoClassId = twoClassifies.stream().map(twoClassify -> {
            return twoClassify.getId();
        }).collect(Collectors.toList());

        List<ThreeClassify> listByParentId = threeClassifyService.getListByParentId(twoClassId);

        //数据处理
        JSONArray jsonArray = new JSONArray();
        for (TwoClassify twoClassify : twoClassifies) {
            JSONArray jsonArray1 = new JSONArray();
            for (ThreeClassify threeClassify : listByParentId) {
                if (threeClassify.getParentId().equals(twoClassify.getId())) {
                    jsonArray1.add(threeClassify);
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", twoClassify);
            jsonObject.put("list", jsonArray1);
            jsonArray.add(jsonObject);
        }
        return new ResponseEntity<>(ResultModel.ok(jsonArray), HttpStatus.OK);
    }

}
