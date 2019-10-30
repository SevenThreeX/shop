package com.sidianzhong.sdz.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sidianzhong.sdz.annotation.BackLoginToken;
import com.sidianzhong.sdz.annotation.UserLoginToken;
import com.sidianzhong.sdz.config.PathConfig;
import com.sidianzhong.sdz.model.OneClassify;
import com.sidianzhong.sdz.utils.ResultModel;
import com.sidianzhong.sdz.utils.ResultStatus;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hxg on 2016/10/06.
 */
@Api(description = "杂项")
@Controller
public class OtherController {

    @Autowired
    HttpServletResponse response;

    @ApiOperation(value = "获取轮播图")
    @RequestMapping(value = "/getBanner",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createOneClassify() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 1; i <= 5; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("commodityId", 1);
            jsonObject.put("img", "http://tva1.sinaimg.cn/large/007X8olVly1g7gg52h6b4j315o0d2neg.jpg");
            jsonArray.add(jsonObject);
        }
        return new ResponseEntity<>(ResultModel.ok(jsonArray), HttpStatus.OK);
    }

    /**
     * 上传图片接口
     */
//    @UserLoginToken
    @ApiOperation(value = "上传图片", notes = "上传图片")
    @RequestMapping(value = "/upLoad", method = RequestMethod.POST)
    @ResponseBody
    public Object sendImage(
//            @RequestHeader(value = "X-Auth-Token") String token,
            @ApiParam(value = "传输图片", required = true)
            @RequestParam(name = "file") MultipartFile file
    ) {
        if (file == null || file.isEmpty())
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK); //验证File是否为空
        try {
            if (ImageIO.read(file.getInputStream()) == null)
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK); //验证File是否是图片
        } catch (IOException e) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK); //验证File是否是图片
        }

        String fileName = PathConfig.IMG_ABSOLUTE_PATH + file.getOriginalFilename();

        //保存图片
        try {
            File dest = new File(fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest); //保存文件
        } catch (IllegalStateException | IOException e) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK); //验证账号是否存在
        }
        return new ResponseEntity<>(ResultModel.ok(file.getOriginalFilename()), HttpStatus.OK);
    }
}
