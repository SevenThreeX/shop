package com.sidianzhong.sdz.controller;

import com.sidianzhong.sdz.annotation.UserLoginToken;
import com.sidianzhong.sdz.model.ShopUser;
import com.sidianzhong.sdz.model.UserRole;
import com.sidianzhong.sdz.model.commond.PhoneCode;
import com.sidianzhong.sdz.model.commond.UserToken;
import com.sidianzhong.sdz.service.ShopUserService;
import com.sidianzhong.sdz.service.UserRoleService;
import com.sidianzhong.sdz.service.commond.RedisService;
import com.sidianzhong.sdz.utils.MD5Utils;
import com.sidianzhong.sdz.utils.Matches;
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
import java.util.Date;
import java.util.List;

@Api(description = "登录相关")
@Controller
public class LoginController {

    @Autowired
    ShopUserService shopUserService;
    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RedisService redisService;


    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/userLogin",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object login(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "password") String password
    ) {
        if (phone == null || phone.isEmpty() || !phone.matches(Matches.PHONE_MATCHES)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PHONE_ERROR), HttpStatus.OK);
        }
        if (password == null || password.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PASSWORD_ERROR), HttpStatus.OK);
        }

        ShopUser user = shopUserService.getUserByPhone(phone);
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_EXITS_ERROR), HttpStatus.OK);
        }

        Integer status = user.getStatus();
        if (status == null || status == 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_IS_FROZEN_ERROR), HttpStatus.OK);
        }

//        if (!password.equals(user.getPassword())) {
//            return new ResponseEntity<>(ResultModel.error("password is error"), HttpStatus.OK);
//        }

        //MD5验证密码
        if (!MD5Utils.checkMD5(user.getPassword(), password)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PASSWORD_ERROR), HttpStatus.OK);
        }

        //登入成功
        UserToken userToken = redisService.createToken(user);
        return new ResponseEntity<>(ResultModel.ok(userToken), HttpStatus.OK);
    }

    @ApiOperation(value = "后台登录")
    @RequestMapping(value = "/backLogin",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object BackLogin(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "password") String password
    ) {
        if (phone == null || phone.isEmpty() || !phone.matches(Matches.PHONE_MATCHES)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PHONE_ERROR), HttpStatus.OK);
        }
        if (password == null || password.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PASSWORD_ERROR), HttpStatus.OK);
        }

        ShopUser user = shopUserService.getUserByPhone(phone);
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_EXITS_ERROR), HttpStatus.OK);
        }

        Integer status = user.getStatus();
        if (status == null || status == 1) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_IS_FROZEN_ERROR), HttpStatus.OK);
        }

        //验证是否操作有权限
        List<UserRole> userByUserId = userRoleService.getUserByUserId(user.getId());
        for (UserRole userRole : userByUserId) {
            if (userRole == null || userRole.getRoleId() == 1) {
                return new ResponseEntity<>(ResultModel.error(ResultStatus.NOT_PERMISSION), HttpStatus.OK);
            }
        }


        //MD5验证密码
        if (!MD5Utils.checkMD5(user.getPassword(), password)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PASSWORD_ERROR), HttpStatus.OK);
        }

        //登入成功
        UserToken userToken = redisService.createToken(user);
        return new ResponseEntity<>(ResultModel.ok(userToken), HttpStatus.OK);
    }

    @ApiOperation(value = "第三方登陆")
    @RequestMapping(value = "/ThirdLogin",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object ThirdLogin(
            @RequestParam(value = "accountCode") String accountCode
    ) {
        if (accountCode == null || accountCode.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_THIRD_ERROR), HttpStatus.OK);
        }

        ShopUser user = shopUserService.getUserByWeChat(accountCode);
        //判断账号是否注册过
        if (user == null) {
            //返回需要绑定账号和密码
            return new ResponseEntity<>(ResultModel.error(ResultStatus.THIRD_LOGIN_ERROR), HttpStatus.OK);
        } else {
            String phone = user.getPhone();
            if (phone == null || phone.isEmpty()) {
                //返回需要绑定账号和密码
                return new ResponseEntity<>(ResultModel.error(ResultStatus.THIRD_LOGIN_ERROR), HttpStatus.OK);
            }
            Integer status = user.getStatus();
            if (status == null || status == 1) {
                return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_IS_EXITS_ERROR), HttpStatus.OK);
            } else {
                //登入成功
                UserToken userToken = redisService.createToken(user);
                return new ResponseEntity<>(ResultModel.ok(userToken), HttpStatus.OK);
            }
        }
    }

    @ApiOperation(value = "绑定手机号")
    @RequestMapping(value = "bindByPhone",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object bindByPhone(
            @RequestParam(value = "accountCode") String accountCode,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "code") String code,
            @RequestParam(value = "nickName") String nickName,
            @RequestParam(value = "picUrl") String picUrl
    ) {
        if (accountCode == null || accountCode.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_THIRD_ERROR), HttpStatus.OK);
        }
        if (phone == null || phone.isEmpty() || !phone.matches(Matches.PHONE_MATCHES)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PHONE_ERROR), HttpStatus.OK);
        }
        if (password == null || password.isEmpty() || !password.matches(Matches.PASSWORD_MATCHES)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PASSWORD_ERROR), HttpStatus.OK);
        }
        if (code == null || code.isEmpty() || !code.matches(Matches.PHONE_CODE_MATCHES) || !redisService.getCode(phone).getCode().equals(code)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_CODE_ERROR), HttpStatus.OK);
        }

        //判断改手机号是否注册过账号
        ShopUser phoneUser = shopUserService.getUserByPhone(phone);
        if (phoneUser != null) {
            //是否绑定过微信
            String wechat = phoneUser.getWechat();
            if (wechat == null || wechat.isEmpty()) {
                return new ResponseEntity<>(ResultModel.error(ResultStatus.PHONE_BIND_ERROR), HttpStatus.OK);
            }
        }

        //判断该微信是否绑定过手机号
        if (shopUserService.getUserByWeChat(accountCode) != null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.WECHAT_BINF_ERROR), HttpStatus.OK);
        } else {
            Date date = new Date();

            ShopUser shopUser = new ShopUser();
            if (phoneUser != null) {
                if (phoneUser.getName() == null) {
                    shopUser.setName(nickName);
                }
                if (phoneUser.getPortrait() == null) {
                    shopUser.setPortrait(picUrl);
                }
            }
            shopUser.setWechat(accountCode);
            shopUser.setStatus(0);
            shopUser.setPhone(phone);
            shopUser.setPassword(password);
            shopUser.setCreateTime(date);

            if ((shopUser = shopUserService.create(shopUser)) == null) {
                return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_THIRD_ERROR), HttpStatus.OK);
            }

            //注册为普通用户
            UserRole userRole = new UserRole();
            userRole.setUserId(shopUser.getId());
            userRole.setRoleId(1);
            userRole.setLastUpdateTime(date);
            userRole.setCreateTime(date);

            if (userRoleService.create(userRole) == null) {
                return new ResponseEntity<>(ResultModel.error(ResultStatus.REGISTER_FAIL_ERROR), HttpStatus.OK);
            }

            redisService.deleteCode(phone);

            //登入成功
            UserToken userToken = redisService.createToken(shopUser);
            return new ResponseEntity<>(ResultModel.ok(userToken), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "注册")
    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object register(
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "code") String code
    ) {
        if (phone == null || phone.isEmpty() || !phone.matches(Matches.PHONE_MATCHES))
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PHONE_ERROR), HttpStatus.OK);
        if (password == null || password.isEmpty() || !password.matches(Matches.PASSWORD_MATCHES))
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PASSWORD_ERROR), HttpStatus.OK);
        if (code == null || code.isEmpty() || !code.matches(Matches.PHONE_CODE_MATCHES) || !redisService.getCode(phone).getCode().equals(code)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_CODE_ERROR), HttpStatus.OK);
        }

        ShopUser user = shopUserService.getUserByPhone(phone);
        if (user != null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_IS_EXITS_ERROR), HttpStatus.OK);
        }

        Date date = new Date();
        //开始注册
        ShopUser shopUser = new ShopUser();
        shopUser.setCreateTime(new Date(System.currentTimeMillis()));
        shopUser.setName(userName == null || userName.isEmpty() ? phone : userName);
        shopUser.setPassword(password);
        shopUser.setPhone(phone);
        shopUser.setStatus(0);
        if ((shopUser = shopUserService.create(shopUser)) == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.REGISTER_FAIL_ERROR), HttpStatus.OK);
        }

        //注册为普通用户
        UserRole userRole = new UserRole();
        userRole.setUserId(shopUser.getId());
        userRole.setRoleId(1);
        userRole.setLastUpdateTime(date);
        userRole.setCreateTime(date);
        if (userRoleService.create(userRole) == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.REGISTER_FAIL_ERROR), HttpStatus.OK);
        }

        redisService.deleteCode(code);
        //注册成功
        return new ResponseEntity<>(ResultModel.ok(true), HttpStatus.OK);
    }

    @ApiOperation(value = "忘记密码")
    @RequestMapping(value = "/forgetPassword",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object forgetPassword(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "newPassword") String password,
            @RequestParam(value = "code") String code
    ) {
        if (phone == null || phone.isEmpty() || !phone.matches(Matches.PHONE_MATCHES)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PHONE_ERROR), HttpStatus.OK);
        }
        if (password == null || password.isEmpty() || !password.matches(Matches.PASSWORD_MATCHES)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PASSWORD_ERROR), HttpStatus.OK);
        }
        if (code == null || code.isEmpty() || !code.matches(Matches.PHONE_CODE_MATCHES) || !redisService.getCode(phone).getCode().equals(code)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_CODE_ERROR), HttpStatus.OK);
        }

        ShopUser user = shopUserService.getUserByPhone(phone);
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_EXITS_ERROR), HttpStatus.OK);
        }

        //开始修改
        user.setPassword(password);
        user.setStatus(0);
        if (shopUserService.update(user) == 0) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.UPDATE_FAIL_ERROR), HttpStatus.OK);
        }

        redisService.deleteCode(phone);

        //修改成功
        return new ResponseEntity<>(ResultModel.ok(true), HttpStatus.OK);

    }

    @UserLoginToken
    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/updatePassword",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object updatePassword(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "newPassword") String newPassword,
            @RequestParam(value = "oldPassword") String oldPassword
    ) {
        Integer user_id = (Integer) request.getAttribute("USER_ID");
        if (newPassword == null || newPassword.isEmpty() || !newPassword.matches(Matches.PASSWORD_MATCHES)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PASSWORD_ERROR), HttpStatus.OK);
        }
        if (oldPassword == null || oldPassword.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.LOGIN_PASSWORD_ERROR), HttpStatus.OK);
        }

        ShopUser user = shopUserService.get(user_id);
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_EXITS_ERROR), HttpStatus.OK);
        }

        //验证历史密码是否正确
        if (!MD5Utils.checkMD5(user.getPassword(), oldPassword)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.UPDATE_PASSWORD_ERROR), HttpStatus.OK);
        }

        //开始修改
        user.setPassword(newPassword);
        user.setStatus(0);
        if (shopUserService.update(user) == 0) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.UPDATE_FAIL_ERROR), HttpStatus.OK);
        }

        //修改成功
        return new ResponseEntity<>(ResultModel.ok(true), HttpStatus.OK);

    }
}
