package com.sidianzhong.sdz.service.commond;

import com.sidianzhong.sdz.utils.ResultModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface PayService {

    //生成订单
    ResponseEntity<ResultModel> payOrder(
            String orderCode,
            Integer money,
            String goods,
            Integer payType
    );

    //支付
    String payWXCallBack(HttpServletRequest request) throws IOException;

    //退款
    Boolean refundWXCallBack(HttpServletRequest request) throws IOException;

    //轮询获取订单支付状态
    ResponseEntity<ResultModel> getPayStatus(Integer orderId);
}
