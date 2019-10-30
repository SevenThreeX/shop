package com.sidianzhong.sdz.controller;

import com.sidianzhong.sdz.annotation.BackLoginToken;
import com.sidianzhong.sdz.annotation.UserLoginToken;
import com.sidianzhong.sdz.interceptor.SpringUtils;
import com.sidianzhong.sdz.model.UserOrder;
import com.sidianzhong.sdz.pay.wxPay.OrderCodeFactory;
import com.sidianzhong.sdz.pay.wxPay.WeiChartUtil;
import com.sidianzhong.sdz.service.UserOrderService;
import com.sidianzhong.sdz.service.commond.PayService;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(description = "支付")
@Controller
public class PayController {

    @Autowired
    PayService payService;
    @Autowired
    UserOrderService userOrderService;

    @Autowired
    private WeiChartUtil weiChartUtil;


    /**
     * 下单
     *
     * @param payType
     * @param money
     * @param goods
     * @return
     */
    @UserLoginToken
    @ApiOperation(value = "下单")
    @RequestMapping(value = "/payOrder",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<ResultModel> payOrder(
            HttpServletRequest request,
            @RequestHeader("X-Auth-Token") String token,
            @RequestParam("payType") Integer payType,
            @RequestParam("addressId") Integer addressId,
            @RequestParam("commodityId") Integer commodityId,
            @RequestParam("money") Integer money,
            @RequestParam("goods") String goods
    ) {
        Integer user_id = (Integer) request.getAttribute("USER_ID");

        UserOrder userOrder = new UserOrder();
        userOrder.setAddressId(addressId);
        userOrder.setCommodityId(commodityId);
        userOrder.setPrice(money);
        userOrder.setStatus(1); //1 待支付 2 支付完成 3订单取消
        userOrder.setUserId(user_id);

        //生成支付订单
        String orderCode = OrderCodeFactory.getOrderCode((long) user_id);
        userOrder.setOrderNum(orderCode);


        System.out.println("******************payOrder******************");
        System.out.println("userId: " + user_id);
        System.out.println("orderCode: " + orderCode);
        System.out.println("addressId: " + addressId);
        System.out.println("commodityId: " + commodityId);
        System.out.println("payType: " + payType);
        System.out.println("money: " + money);
        System.out.println("goods: " + goods);
        System.out.println("************************************");
        ResponseEntity<ResultModel> resultModelResponseEntity = payService.payOrder(orderCode, money, goods, payType);
        ResultModel body = resultModelResponseEntity.getBody();
        Map<String, String> data = (Map<String, String>) body.getData();
        if (data != null && data.size() != 0) {
            String prepayId = data.get("prepayid");
            userOrder.setPayNum(prepayId);
            if (userOrderService.create(userOrder) == null) {
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
            }
        }
        return resultModelResponseEntity;
    }

    @PostMapping("/payWXCallBack")
    public String payWXCallBack(HttpServletRequest request) {
        System.out.println("******************payWXCallBack******************");
        System.out.println("************************************");
        try {
            String orderCode = payService.payWXCallBack(request);
            UserOrder userOrder = userOrderService.getOrderByOrderCode(orderCode);
            if (userOrder != null && userOrder.getStatus() == 1) {
                userOrder.setStatus(2); //设置支付完成
                if (userOrderService.update(userOrder) == 0) {
                    System.out.println("用户支付成功");
                    Map<String, String> map = new HashMap<>();
                    map.put("return_code", "SUCCESS");
                    map.put("return_msg", "OK");
                    return weiChartUtil.creatXml(map);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/refundWXCallBack")
    public String refundWXCallBack(HttpServletRequest request) {
        System.out.println("******************refundWXCallBack******************");
        System.out.println("************************************");
        try {
            if (payService.refundWXCallBack(request)) {
                System.out.println("用户退款成功");
                Map<String, String> map = new HashMap<>();
                map.put("return_code", "SUCCESS");
                map.put("return_msg", "OK");
                return weiChartUtil.creatXml(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
