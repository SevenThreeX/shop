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
@Api(description = "我的地址")
@Controller
public class MyAddressController {

    @Autowired
    MyAddressService myAddressService;

    @Autowired
    HttpServletResponse response;

    @UserLoginToken
    @ApiOperation(value = "创建'我的地址'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/my_addresses/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createMyAddress(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "county", required = false) String county,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime
    ) {
        Integer user_Id = (Integer) request.getAttribute("USER_ID");
        System.out.println(user_Id);
        MyAddress item = new MyAddress();
        Date date = new Date();
        if (user_Id != null) {
            item.setUserId(user_Id);
        }
        if (province != null) {
            item.setProvince(province);
        }
        if (city != null) {
            item.setCity(city);
        }
        if (county != null) {
            item.setCounty(county);
        }
        if (address != null) {
            item.setAddress(address);
        }
        if (phone != null) {
            item.setPhone(phone);
        }
        if (name != null) {
            item.setName(name);
        }
        item.setStatus(status == null ? 0 : status);
        item.setCreateTime(date);
        item.setLastUpdateTime(date);
        if (myAddressService.create(item) == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "删除'我的地址'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/my_addresses/delete",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteMyAddress(
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "my_address_id") Integer id
    ) {
        MyAddress item = myAddressService.get(id);
        if (null == item || myAddressService.delete(id) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @BackLoginToken
    @ApiOperation(value = "修改'我的地址'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/my_addresses/edit",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editMyAddress(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "county", required = false) String county,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        MyAddress item = myAddressService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        if (userId != null) {
            item.setUserId(userId);
        }
        if (province != null) {
            item.setProvince(province);
        }
        if (city != null) {
            item.setCity(city);
        }
        if (county != null) {
            item.setCounty(county);
        }
        if (address != null) {
            item.setAddress(address);
        }
        if (phone != null) {
            item.setPhone(phone);
        }
        if (name != null) {
            item.setName(name);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        if (myAddressService.update(item) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "查询'我的地址'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/getMyAddressById",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getMyAddressById(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token
    ) {
        Integer user_id = (Integer) request.getAttribute("USER_ID");
        List<MyAddress> listByUserId = myAddressService.getListByUserId(user_id);
        if (null == listByUserId) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(listByUserId), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "查询'我的地址'表中的多条记录或者新增某条记录", notes = "get传参：根据所需的字段进行匹配查询。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder； ")
    @RequestMapping(value = "/my_addresses",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getMyAddresses(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortItem", required = false, defaultValue = "id") String sortItem,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "county", required = false) String county,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        MyAddress item = new MyAddress();
        if (userId != null) {
            item.setUserId(userId);
        }
        if (province != null) {
            item.setProvince(province);
        }
        if (city != null) {
            item.setCity(city);
        }
        if (county != null) {
            item.setCounty(county);
        }
        if (address != null) {
            item.setAddress(address);
        }
        if (phone != null) {
            item.setPhone(phone);
        }
        if (name != null) {
            item.setName(name);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        PageInfo<MyAddress> list = myAddressService.getListWithPaging(pageNum, pageSize, sortItem, sortOrder, item);
        return new ResponseEntity<>(ResultModel.ok(list), HttpStatus.OK);

    }


    @UserLoginToken
    @ApiOperation(value = "查询'我的地址'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/getMyAddressList",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getMyAddressList(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "status", required = false, defaultValue = "0") Integer status
    ) {
        Integer user_id = (Integer) request.getAttribute("USER_ID");
        List<MyAddress> listByUserId = myAddressService.getListByUserId(user_id);
        if (status != null && status == 1) {
            for (MyAddress myAddress : listByUserId) {
                if (myAddress.getStatus().equals(status)) {
                    listByUserId.clear();
                    listByUserId.add(myAddress);
                    break;
                }
            }
        }

        if (null == listByUserId) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(listByUserId), HttpStatus.OK);
    }
}
