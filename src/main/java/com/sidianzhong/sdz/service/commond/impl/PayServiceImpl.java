package com.sidianzhong.sdz.service.commond.impl;

import com.alibaba.fastjson.JSONObject;
import com.sidianzhong.sdz.pay.util.AESUtil;
import com.sidianzhong.sdz.pay.wxPay.*;
import com.sidianzhong.sdz.service.commond.PayService;
import com.sidianzhong.sdz.utils.ResultModel;
import com.sidianzhong.sdz.utils.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class PayServiceImpl implements PayService {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(WeiChartUtil.class);

    @Autowired
    private WeiChartUtil weiChartUtil;

    @Override
    public ResponseEntity<ResultModel> payOrder(
            String orderCode,
            Integer money,
            String goods,
            Integer payType
    ) {
        //未支付或支付失败才能进行支付
        switch (payType) {
            case 0://微信
                Map<String, String> preyIdMap = weiChartUtil.getPreyId(
                        orderCode,
                        money,
                        goods
                );
                for (String s : preyIdMap.keySet()) {
                    System.out.println(s + ": " + preyIdMap.get(s));
                }
                Map<String, String> map = new HashMap<>();
                map.put("appid", preyIdMap.get("appid"));
                map.put("partnerid", preyIdMap.get("mch_id"));
                map.put("prepayid", preyIdMap.get("prepay_id"));
                map.put("noncestr", preyIdMap.get("nonce_str"));
                map.put("package", "Sign=WXPay");
                map.put("timestamp", new Date().getTime() / 1000 + "");
                map.put("sign", weiChartUtil.getSign(map));

                return new ResponseEntity<>(ResultModel.ok(map), HttpStatus.OK);
            case 1://支付宝
                System.out.println("暂未开通支付宝支付");
                return new ResponseEntity<>(ResultModel.error(ResultStatus.NO_ALIPAY_WITHDRAW_HAS_BEEN_OPENED_YET), HttpStatus.OK);
            default:
                System.out.println("支付类型错误");
                return new ResponseEntity<>(ResultModel.error(ResultStatus.PAYMENT_TYPE_ERROR), HttpStatus.OK);
        }
    }

    @Override
    public String payWXCallBack(HttpServletRequest request) throws IOException {
        String sb = weiChartUtil.reciverWx(request);
        //解析xml成map
        Map<String, String> xmlMap;
        xmlMap = weiChartUtil.getInfoByXml(sb);  //此方法里已经验证过是否是微信返回的签名
        //判断签名是否正确
        if (xmlMap != null) {
            if ("SUCCESS".equals(xmlMap.get("return_code"))) {
                System.out.println("**********************************payWXCallBack***************************************");
                for (String s : xmlMap.keySet()) {
                    System.out.println(s + ": " + xmlMap.get(s));
                }
                System.out.println("*************************************************************************");
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = xmlMap.get("mch_id");
                String openid = xmlMap.get("openid");
                String is_subscribe = xmlMap.get("is_subscribe");
                String out_trade_no = xmlMap.get("out_trade_no");    //这个就是我们内部传给微信的订单号
                String total_fee = xmlMap.get("total_fee");
                LOGGER.info("is_subscribe:{}, out_trade_no: {}, total_fee: {},mch_id: {}, openid: {}", is_subscribe, out_trade_no, total_fee, mch_id, openid);
                return out_trade_no;
            } else {
                LOGGER.info("通知支付失败");
                return "通知支付失败";
            }
        } else {
            LOGGER.info("通知签名验证失败");
            return "通知签名验证失败";
        }

    }

    @Override
    public Boolean refundWXCallBack(HttpServletRequest request) {
        try {
            String A = weiChartUtil.reciverWx(request);
            //解析xml成map
            Map<String, String> xmlMap;
            xmlMap = weiChartUtil.getInfoByXml(A);  //此方法里已经验证过是否是微信返回的签名
            //判断签名是否正确
            if (xmlMap != null) {
                if ("SUCCESS".equals(xmlMap.get("result_code"))) {
                    System.out.println("**********************************refundWXCallBack***************************************");
                    for (String s : xmlMap.keySet()) {
                        System.out.println(s + ": " + xmlMap.get(s));
                    }
                    System.out.println("*************************************************************************");
                }
                String reqInfo = xmlMap.get("req_info");
                String decryptData = AESUtil.decryptData(WeChatAppConfig.bizAppSecret, reqInfo);
                Map<String, String> infoByXml = weiChartUtil.getInfoByXml(decryptData);//解密后的map

//                // 这里是支付成功
//                //////////执行自己的业务逻辑////////////////
//                String out_trade_no = infoByXml.get("out_trade_no");
//                String transaction_id = infoByXml.get("transaction_id");
//
//                TableOrderExample orderExample = new TableOrderExample();
//                orderExample.createCriteria().andOrderCodeEqualTo(out_trade_no);
//                TableOrder tableOrder = orderMapper.selectByExample(orderExample).get(0);
//                tableOrder.setPayStatus(3);
//                tableOrder.setPayCode(transaction_id == null ? "" : transaction_id);
//
//
//                TableActivitiesTicketOrderExample ticketOrderExample = new TableActivitiesTicketOrderExample();
//                ticketOrderExample.createCriteria().andOrderIdEqualTo(tableOrder.getId());
//                TableActivitiesTicketOrder ticketOrder = activitiesTicketOrderMapper.selectByExample(ticketOrderExample).get(0);
//                ticketOrder.setSettlementFlag(2);
//                int j = activitiesTicketOrderMapper.updateByPrimaryKey(ticketOrder);
//
//                int i = orderMapper.updateByPrimaryKey(tableOrder);
//                return i == 1 && j == 1;
                return false;
            } else {
                System.out.println("退款签名无效");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ResponseEntity<ResultModel> getPayStatus(Integer orderId) {
//        TableOrder tableOrder = orderMapper.selectByPrimaryKey(orderId);
//        if (tableOrder == null) {
//            System.out.println("订单不存在");
//            return new ResponseEntity<>(ResultModel.error(ResultStatus.NOTE_ORDERS_DO_NOT_EXIST), HttpStatus.OK);
//        }
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("payStatus", tableOrder.getPayStatus());

        return new ResponseEntity<>(ResultModel.ok(jsonObject), HttpStatus.OK);
    }

}
