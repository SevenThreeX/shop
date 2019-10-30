package com.sidianzhong.sdz.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sidianzhong.sdz.annotation.BackLoginToken;
import com.sidianzhong.sdz.annotation.UserLoginToken;
import com.sidianzhong.sdz.model.*;
import com.sidianzhong.sdz.pay.wxPay.OrderCodeFactory;
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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by hxg on 2016/10/06.
 */
@Api(description = "订单")
@Controller
public class UserOrderController {

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    HttpServletResponse response;

    @UserLoginToken
    @ApiOperation(value = "创建'订单'表中一条信息", notes = "通过post方法请求，传入表中字段的对应信息，创建一条数据。并返回给View层")
    @RequestMapping(value = "/user_orders/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createUserOrder(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "commodityId", required = false) Integer commodityId,
            @RequestParam(value = "addressId", required = false) Integer addressId,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "orderNum", required = false) String orderNum,
            @RequestParam(value = "payNum", required = false) String payNum,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime
    ) {
        UserOrder item = new UserOrder();
        Date date = new Date();

        if (commodityId != null) {
            item.setCommodityId(commodityId);
        }
        if (addressId != null) {
            item.setAddressId(addressId);
        }
        if (price != null) {
            item.setPrice(price);
        }

        Integer user_id = (Integer) request.getAttribute("USER_ID");
        item.setUserId(user_id);
        //生成订单号
        item.setOrderNum(OrderCodeFactory.getOrderCode((long) user_id));
        item.setPayNum(null);
        item.setStatus(status == null ? 0 : status);
        item.setCreateTime(date);
        item.setLastUpdateTime(date);
        if (userOrderService.create(item) == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "删除'订单'表中的某条记录", notes = "根据url传入的数据id，删除整条记录。")
    @RequestMapping(value = "/user_orders/delete",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteUserOrder(
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "user_order_id") Integer id
    ) {
        UserOrder item = userOrderService.get(id);
        if (null == item || userOrderService.delete(id) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @BackLoginToken
    @ApiOperation(value = "修改'订单'表中的某条记录", notes = "根据url传入的数据id，确定修改表中的某条记录，传入表中字段要修改的信息，不传代表不修改。并返回给View层")
    @RequestMapping(value = "/user_orders/edit",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editUserOrder(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "commodityId", required = false) Integer commodityId,
            @RequestParam(value = "addressId", required = false) Integer addressId,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "orderNum", required = false) String orderNum,
            @RequestParam(value = "payNum", required = false) String payNum,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        UserOrder item = userOrderService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        if (userId != null) {
            item.setUserId(userId);
        }
        if (commodityId != null) {
            item.setCommodityId(commodityId);
        }
        if (addressId != null) {
            item.setAddressId(addressId);
        }
        if (price != null) {
            item.setPrice(price);
        }
        if (orderNum != null) {
            item.setOrderNum(orderNum);
        }
        if (payNum != null) {
            item.setPayNum(payNum);
        }
        if (status != null) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());
        if (userOrderService.update(item) != 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @UserLoginToken
    @ApiOperation(value = "查询'订单'表中的某条记录", notes = "根据url传入的数据id，查询对应的一条数据。")
    @RequestMapping(value = "/getUserOrderById",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getUserOrderById(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "id") Integer id
    ) {

        UserOrder item = userOrderService.get(id);
        if (null == item) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResultModel.ok(item), HttpStatus.OK);

    }

    @Autowired
    CommodityService commodityService;


    @UserLoginToken
    @ApiOperation(value = "查询'订单'表中的多条记录或者新增某条记录", notes = "get传参：根据所需的字段进行匹配查询。数据数量取决于pageNo和pageSize；数据的先后顺序取决于sortItem，sortOrder； ")
    @RequestMapping(value = "/user_orders",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getUserOrders(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortItem", required = false, defaultValue = "id") String sortItem,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "commodityId", required = false) Integer commodityId,
            @RequestParam(value = "addressId", required = false) Integer addressId,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "orderNum", required = false) String orderNum,
            @RequestParam(value = "payNum", required = false) String payNum,
            @RequestParam(value = "status", required = false) Integer status
            //0 All  1待付款 2待发货 3待收货 4已完成
    ) {
        Integer user_id = (Integer) request.getAttribute("USER_ID");
        UserOrder item = new UserOrder();
        item.setUserId(user_id);

        if (commodityId != null) {
            item.setCommodityId(commodityId);
        }
        if (addressId != null) {
            item.setAddressId(addressId);
        }
        if (price != null) {
            item.setPrice(price);
        }
        if (orderNum != null) {
            item.setOrderNum(orderNum);
        }
        if (payNum != null) {
            item.setPayNum(payNum);
        }
        if (status != null && status != 0) {
            item.setStatus(status);
        }
        item.setLastUpdateTime(new Date());

        PageInfo<UserOrder> list = userOrderService.getListWithPaging(pageNum, pageSize, sortItem, sortOrder, item);

        List<Integer> collect = list.getList().stream().map(UserOrder::getCommodityId).distinct().collect(Collectors.toList());
        List<Commodity> listByIds = commodityService.getListByIds(collect);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.getList().size(); i++) {
            JSONObject jsonObject = new JSONObject();
            UserOrder userOrder = list.getList().get(i);
            jsonObject.put("orderPrice", userOrder.getPrice());
            jsonObject.put("createTime", userOrder.getCreateTime());
            jsonObject.put("orderCode", userOrder.getOrderNum());
            jsonObject.put("orderStatus", userOrder.getStatus());
            jsonObject.put("orderId", userOrder.getId());

            for (int i1 = 0; i1 < listByIds.size(); i1++) {
                Commodity commodity = listByIds.get(i1);
                if (commodity.getId().equals(userOrder.getCommodityId())) {
                    String commodityName = commodity.getName();
                    jsonObject.put("commodityName", commodityName);
                    try {
                        List<String> parse = (List<String>) JSONArray.parse(commodity.getImg());
                        String commodityImg = parse.get(0);
                        jsonObject.put("commodityImg", commodityImg);
                    } catch (Exception e) {
                        jsonObject.put("commodityImg", null);
                    }
                    jsonObject.put("commodityPrice", commodity.getPrice());
                }
            }

            jsonArray.add(jsonObject);
        }
        return new ResponseEntity<>(ResultModel.ok(jsonArray), HttpStatus.OK);

    }

    @Autowired
    MyAddressService addressService;
    @Autowired
    CommodityPostService commodityPostService;

    @UserLoginToken
    @ApiOperation(value = "获取订单详情")
    @RequestMapping(value = "/getOrderDetail",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getOrderDetail(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "orderId") Integer orderId
    ) {
        Integer user_id = (Integer) request.getAttribute("USER_ID");

        JSONObject jsonObject = new JSONObject();
        //获取到订单信息
        UserOrder userOrder = userOrderService.get(orderId);
        if (userOrder == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        if (!userOrder.getUserId().equals(user_id)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        jsonObject.put("order", userOrder);

        //获取到地址信息
        MyAddress myAddress = addressService.get(userOrder.getAddressId());
        jsonObject.put("address", myAddress);

        //获取到商品信息
        Commodity commodity = commodityService.get(userOrder.getCommodityId());
        jsonObject.put("commodity", commodity);
        try {
            List<String> parse = (List<String>) JSONArray.parse(commodity.getImg());
            String commodityImg = parse.get(0);
            commodity.setImg(commodityImg);
        } catch (Exception ignored) {
        }

        //获取到运费
        List<CommodityPost> postByCommodityId = commodityPostService.getPostByCommodityId(userOrder.getCommodityId());
        if (postByCommodityId != null && postByCommodityId.size() != 0) {
            CommodityPost commodityPost = postByCommodityId.get(0);
            jsonObject.put("post", commodityPost);
        }

        return new ResponseEntity<>(ResultModel.ok(jsonObject), HttpStatus.OK);

    }
}
