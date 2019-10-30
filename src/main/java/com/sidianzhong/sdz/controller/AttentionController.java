package com.sidianzhong.sdz.controller;

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
@Api(description = "关注")
@Controller
public class AttentionController {

    @Autowired
    AttentionService attentionService;

    @Autowired
    HttpServletResponse response;

    @UserLoginToken
    @ApiOperation(value = "创建'关注'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/attentions/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createAttention(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "attentionId", required = false) Integer attentionId,
            @RequestParam(value = "status", required = false, defaultValue = "0") Integer status
    ) {
        Integer userId = (Integer) request.getAttribute("USER_ID");
        Date date = new Date();

        Attention item = new Attention();
        if (attentionId != null) {
            item.setAttentionId(attentionId);
        }

        item.setStatus(status == null ? 0 : status);
        item.setUserId(userId);
        item.setCreateTime(date);
        item.setLastUpdateTime(date);

        if (attentionService.create(item) == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "删除'关注'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/attentions/delete",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteAttention(
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "attention_id") Integer id
    ) {
        Attention item = attentionService.get(id);
        if (null == item || attentionService.delete(id) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @BackLoginToken
    @ApiOperation(value = "修改'关注'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/attentions/edit",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editAttention(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "attentionId", required = false) Integer attentionId,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        Date date = new Date();
        Attention item = attentionService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        if (attentionId != null) {
            item.setAttentionId(attentionId);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(date);
        if (attentionService.update(item) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "查询'关注'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/getAttentionById",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getAttentionById(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "id") Integer id
    ) {
        Attention item = attentionService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(item), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "查询'关注'表中的多条记录或者新增某条记录", notes = "get传参：根据所需的字段进行匹配查询。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder； ")
    @RequestMapping(value = "/attentions",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getAttentions(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortItem", required = false, defaultValue = "id") String sortItem,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "attentionId", required = false) Integer attentionId,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        Attention item = new Attention();
        if (userId != null) {
            item.setUserId(userId);
        }
        if (attentionId != null) {
            item.setAttentionId(attentionId);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        PageInfo<Attention> list = attentionService.getListWithPaging(pageNum, pageSize, sortItem, sortOrder, item);
        return new ResponseEntity<>(ResultModel.ok(list), HttpStatus.OK);

    }
}
