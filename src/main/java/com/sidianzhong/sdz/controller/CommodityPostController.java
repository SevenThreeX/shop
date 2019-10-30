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
@Api(description = "商品邮寄")
@Controller
public class CommodityPostController {

    @Autowired
    CommodityPostService commodityPostService;

    @Autowired
    HttpServletResponse response;

    @BackLoginToken
    @ApiOperation(value = "创建'商品邮寄'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/commodity_posts/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createCommodityPost(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "commodityId", required = false) Integer commodityId,
            @RequestParam(value = "post", required = false) String post,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "status", required = false, defaultValue = "0") Integer status,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime
    ) {
        CommodityPost item = new CommodityPost();
        Date date = new Date();
        if (commodityId != null) {
            item.setCommodityId(commodityId);
        }
        if (post != null) {
            item.setPost(post);
        }
        if (price != null) {
            item.setPrice(price);
        }
        item.setStatus(status == null ? 0 : status);
        item.setCreateTime(date);
        item.setLastUpdateTime(date);
        if (commodityPostService.create(item) == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @BackLoginToken
    @ApiOperation(value = "删除'商品邮寄'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/commodity_posts/delete",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteCommodityPost(
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "commodity_post_id") Integer id
    ) {
        CommodityPost item = commodityPostService.get(id);
        if (null == item || commodityPostService.delete(id) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @BackLoginToken
    @ApiOperation(value = "修改'商品邮寄'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/commodity_posts/edit",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editCommodityPost(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "commodityId", required = false) Integer commodityId,
            @RequestParam(value = "post", required = false) String post,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        CommodityPost item = commodityPostService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        if (commodityId != null) {
            item.setCommodityId(commodityId);
        }
        if (post != null) {
            item.setPost(post);
        }
        if (price != null) {
            item.setPrice(price);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        if (commodityPostService.update(item) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "查询'商品邮寄'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/getCommodityPostById",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCommodityPostById(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "id") Integer id
    ) {

        CommodityPost item = commodityPostService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResultModel.ok(item), HttpStatus.OK);

    }

    @UserLoginToken
    @ApiOperation(value = "查询'商品邮寄'表中的多条记录或者新增某条记录", notes = "get传参：根据所需的字段进行匹配查询。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder； ")
    @RequestMapping(value = "/commodity_posts",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getCommodityPosts(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortItem", required = false, defaultValue = "id") String sortItem,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(value = "commodityId", required = false) Integer commodityId,
            @RequestParam(value = "post", required = false) String post,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        CommodityPost item = new CommodityPost();
        if (commodityId != null) {
            item.setCommodityId(commodityId);
        }
        if (post != null) {
            item.setPost(post);
        }
        if (price != null) {
            item.setPrice(price);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        PageInfo<CommodityPost> list = commodityPostService.getListWithPaging(pageNum, pageSize, sortItem, sortOrder, item);
        return new ResponseEntity<>(ResultModel.ok(list), HttpStatus.OK);

    }
}
