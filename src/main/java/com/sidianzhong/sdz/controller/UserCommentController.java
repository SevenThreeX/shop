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
import java.util.stream.Collectors;

/**
 * Created by hxg on 2016/10/06.
 */
@Api(description = "用户评论")
@Controller
public class UserCommentController {

    @Autowired
    UserCommentService userCommentService;
    @Autowired
    ShopUserService shopUserService;
    @Autowired
    UserLikeService userLikeService;

    @Autowired
    HttpServletResponse response;

    @UserLoginToken
    @ApiOperation(value = "创建'用户评论'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/user_comments/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createUserComment(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "commodityId", required = false) Integer commodityId,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime
    ) {
        UserComment item = new UserComment();
        Date date = new Date();
        if (userId != null) {
            item.setUserId(userId);
        }
        if (commodityId != null) {
            item.setCommodityId(commodityId);
        }
        if (comment != null) {
            item.setComment(comment);
        }
        item.setStatus(status == null ? 0 : status);
        item.setCreateTime(date);
        item.setLastUpdateTime(date);
        if (userCommentService.create(item) == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "删除'用户评论'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/user_comments/delete",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteUserComment(
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "user_comment_id") Integer id
    ) {
        UserComment item = userCommentService.get(id);
        if (null == item || userCommentService.delete(id) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @BackLoginToken
    @ApiOperation(value = "修改'用户评论'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/user_comments/edit",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editUserComment(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "commodityId", required = false) Integer commodityId,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        UserComment item = userCommentService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        if (userId != null) {
            item.setUserId(userId);
        }
        if (commodityId != null) {
            item.setCommodityId(commodityId);
        }
        if (comment != null) {
            item.setComment(comment);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        if (userCommentService.update(item) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "查询'用户评论'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/getUserCommentById",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getUserCommentById(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "id") Integer id
    ) {

        UserComment item = userCommentService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResultModel.ok(item), HttpStatus.OK);

    }

    @ApiOperation(value = "查询'用户评论'表中的多条记录或者新增某条记录", notes = "get传参：根据所需的字段进行匹配查询。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder； ")
    @RequestMapping(value = "/user_comments",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getUserComments(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortItem", required = false, defaultValue = "id") String sortItem,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "commodityId", required = false) Integer commodityId,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "status", required = false) Integer status
    ) {


        UserComment item = new UserComment();
        if (userId != null) {
            item.setUserId(userId);
        }
        if (commodityId != null) {
            item.setCommodityId(commodityId);
        }
        if (comment != null) {
            item.setComment(comment);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        PageInfo<UserComment> list = userCommentService.getListWithPaging(pageNum, pageSize, sortItem, sortOrder, item);

        //获取到分页数据后的所有id数量
        List<Integer> userCommentId = list.getList().stream().distinct().map(userComment -> {
            return userComment.getUserId();
        }).distinct().collect(Collectors.toList());

        //获取到用户信息
        List<ShopUser> userById = shopUserService.getUserById(userCommentId);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < userById.size(); i++) {
            ShopUser shopUser = userById.get(i);
            UserComment userComment = list.getList().get(i);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("createTime", userComment.getCreateTime());
            jsonObject1.put("content", userComment.getComment());
            jsonObject1.put("userName", shopUser.getName());
            jsonObject1.put("userId", shopUser.getId());
            jsonObject1.put("icon", shopUser.getPortrait());
            //获取到用户点赞
            Integer countByUserId = userLikeService.getCountByUserId(userComment.getCommodityId());
            jsonObject1.put("likeCount", countByUserId);

            jsonArray.add(jsonObject1);
        }


        return new ResponseEntity<>(ResultModel.ok(jsonArray), HttpStatus.OK);
    }
}
