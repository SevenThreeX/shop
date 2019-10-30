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

/**
 * Created by hxg on 2016/10/06.
 */
@Api(description = "总分类")
@Controller
public class ClassifyController {

    @Autowired
    ClassifyService classifyService;
    @Autowired
    OneClassifyService oneClassifyService;

    @Autowired
    HttpServletResponse response;

    @BackLoginToken
    @ApiOperation(value = "创建'总分类'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/classifies/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createClassify(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "status", required = false, defaultValue = "0") Integer status
    ) {
        Date date = new Date();

        Classify item = new Classify();
        if (name != null) {
            item.setName(name);
        }
        if (content != null) {
            item.setContent(content);
        }

        item.setStatus(status == null ? 0 : status);
        item.setCreateTime(date);
        item.setLastUpdateTime(date);

        if (classifyService.create(item) == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @BackLoginToken
    @ApiOperation(value = "删除'总分类'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/classifies/delete",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteClassify(
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "classify_id") Integer id
    ) {
        Classify item = classifyService.get(id);
        if (null == item || classifyService.delete(id) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @BackLoginToken
    @ApiOperation(value = "修改'总分类'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/classifies/edit",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editClassify(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        Classify item = classifyService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        if (name != null) {
            item.setName(name);
        }
        if (content != null) {
            item.setContent(content);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        if (classifyService.update(item) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "查询'总分类'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/getClassifyById",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getClassifyById(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "id") Integer id
    ) {

        Classify item = classifyService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResultModel.ok(item), HttpStatus.OK);

    }

    @UserLoginToken
    @ApiOperation(value = "查询'总分类'表中的多条记录或者新增某条记录", notes = "get传参：根据所需的字段进行匹配查询。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder； ")
    @RequestMapping(value = "/classifies",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getClassifies(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortItem", required = false, defaultValue = "id") String sortItem,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        Classify item = new Classify();
        if (name != null) {
            item.setName(name);
        }
        if (content != null) {
            item.setContent(content);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        PageInfo<Classify> list = classifyService.getListWithPaging(pageNum, pageSize, sortItem, sortOrder, item);
        return new ResponseEntity<>(ResultModel.ok(list), HttpStatus.OK);

    }


    @ApiOperation(value = "查询首页列表")
    @RequestMapping(value = "/getHomeClassifyList",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getClassifies() {
        JSONArray jsonArray = new JSONArray();
        List<Classify> all = classifyService.getAll();
        for (Classify classify : all) {
            JSONObject jsonObject = new JSONObject();
            List<OneClassify> listByParentId = oneClassifyService.getListByParentId(classify.getId());
            jsonObject.put("title",classify);
            jsonObject.put("list",listByParentId);
            jsonArray.add(jsonObject);
        }
        return new ResponseEntity<>(ResultModel.ok(jsonArray), HttpStatus.OK);
    }
}
