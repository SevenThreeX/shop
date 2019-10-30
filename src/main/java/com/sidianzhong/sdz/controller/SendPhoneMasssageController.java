package com.sidianzhong.sdz.controller;

import com.alibaba.fastjson.JSONObject;
import com.sidianzhong.sdz.model.commond.PhoneCode;
import com.sidianzhong.sdz.service.commond.RedisService;
import com.sidianzhong.sdz.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;

@Api(description = "发送短信")
@Controller
public class SendPhoneMasssageController {

    @Autowired
    RedisService redisService;


    @ApiOperation(value = "发送手机短信")
    @RequestMapping(value = "/sendPhoneMassage",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object login(
            @RequestParam(value = "phone") String phone
    ) throws IOException {
        long nowDate = new Date().getTime();
        String str = String.valueOf(nowDate);
        String code = str.substring(7);
        PhoneCode code1 = redisService.getCode(phone);
        if (null != code1.getCode()) {
            long nowTime = System.currentTimeMillis();
            long lastTime = code1.getCurrentTime();
            if (((nowTime - lastTime)) < 30 * 1000) {
                return new ResponseEntity<>(ResultModel.error(ResultStatus.SENDMASSAGE_AGAIN), HttpStatus.OK);
            }
        }
        int i = SendPhoneMessage.sendMsg(phone, code);
        PhoneCode phoneCode = redisService.createCode(phone, code);
        JSONObject jsonObjectRet = new JSONObject();
        if (i == 200) {
            jsonObjectRet.put("result", "成功");
            return new ResponseEntity<>(ResultModel.ok(jsonObjectRet), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.SENDMASSAGE_ERROR), HttpStatus.OK);
        }
    }
}
