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
import org.apache.catalina.User;
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
@Api(description = "用户")
@Controller
public class ShopUserController {

    @Autowired
    ShopUserService shopUserService;
    @Autowired
    MyCollectionService myCollectionService;
    @Autowired
    AttentionService attentionService;
    @Autowired
    ShopRoleService shopRoleService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    FootprintService footprintService;

    @Autowired
    HttpServletResponse response;

    @BackLoginToken
    @ApiOperation(value = "创建'用户'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/shop_users/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createShopUser(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "portrait", required = false) String portrait,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "wechat", required = false) String wechat,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime
    ) {
        ShopUser item = new ShopUser();
        Date date = new Date();
        if (name != null) {
            item.setName(name);
        }
        if (password != null) {
            item.setPassword(password);
        }
        if (phone != null) {
            item.setPhone(phone);
        }
        if (portrait != null) {
            item.setPortrait(portrait);
        }
        if (wechat != null) {
            item.setWechat(wechat);
        }
        item.setStatus(status == null ? 0 : status);
        item.setCreateTime(date);
        item.setLastUpdateTime(date);
        if (shopUserService.create(item) == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

//    @BackLoginToken
//    @ApiOperation(value = "删除'用户'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
//    @RequestMapping(value = "/shop_users/delete",
//            method = RequestMethod.POST,
//            produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public Object deleteShopUser(
//            @RequestHeader(value = "X-Auth-Token") String token,
//            @RequestParam(value = "shop_user_id") Integer id
//    ) {
//        ShopUser item = shopUserService.get(id);
//        //首先删除所有关联表
//        identityService.delete()
//
//        if (null == item || shopUserService.delete(id) != 1) {
//            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
//    }

    @UserLoginToken
    @ApiOperation(value = "修改'用户'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/shop_users/edit",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editShopUser(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "portrait", required = false) String portrait,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        Integer user_id = (Integer) request.getAttribute("USER_ID");
        ShopUser item = shopUserService.get(user_id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        if (name != null) {
            item.setName(name);
        }
        if (phone != null) {
            item.setPhone(phone);
        }
        if (portrait != null) {
            item.setPortrait(portrait);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        if (shopUserService.update(item) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }


    @UserLoginToken
    @ApiOperation(value = "查询'用户'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/getShopUserById",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getShopUserById(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token
    ) {
        Integer user_id = (Integer) request.getAttribute("USER_ID");

        ShopUser item = shopUserService.get(user_id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(item);

        //关注
        Attention attention = new Attention();
        attention.setUserId(user_id);
        int attentionCount = attentionService.getCount(attention);

        //粉丝
        Attention attention1 = new Attention();
        attention1.setAttentionId(user_id);
        int fansCount = attentionService.getCount(attention1);

        //收藏
        MyCollection myCollection = new MyCollection();
        myCollection.setUserId(user_id);
        int collectioncount = myCollectionService.getCount(myCollection);

        //足迹
        Footprint footprint = new Footprint();
        footprint.setUserId(user_id);
        int footprintCount = footprintService.getCount(footprint);

        //身份
        List<UserRole> userByUserId = userRoleService.getUserByUserId(user_id);
        List<Integer> collect = userByUserId.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        List<ShopRole> listByRoleIds = shopRoleService.getListByRoleIds(collect);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("attentionCount", attentionCount);
        jsonObject.put("fansCount", fansCount);
        jsonObject.put("collectionCount", collectioncount);
        jsonObject.put("footprintCount", footprintCount);
        jsonObject.put("user", jsonArray);
        jsonObject.put("role", listByRoleIds);
        return new ResponseEntity<>(ResultModel.ok(jsonObject), HttpStatus.OK);
    }

    @BackLoginToken
    @ApiOperation(value = "查询'用户'表中的多条记录或者新增某条记录", notes = "get传参：根据所需的字段进行匹配查询。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder； ")
    @RequestMapping(value = "/shop_users",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getShopUsers(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortItem", required = false, defaultValue = "id") String sortItem,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "portrait", required = false) String portrait,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "wechat", required = false) String wechat
    ) {
        ShopUser item = new ShopUser();
        if (name != null) {
            item.setName(name);
        }
        if (password != null) {
            item.setPassword(password);
        }
        if (phone != null) {
            item.setPhone(phone);
        }
        if (portrait != null) {
            item.setPortrait(portrait);
        }
        if (status != null) {
            item.setStatus(status);
        }
        if (wechat != null) {
            item.setWechat(wechat);
        }
        item.setLastUpdateTime(new Date());
        PageInfo<ShopUser> list = shopUserService.getListWithPaging(pageNum, pageSize, sortItem, sortOrder, item);
        return new ResponseEntity<>(ResultModel.ok(list), HttpStatus.OK);

    }
}
