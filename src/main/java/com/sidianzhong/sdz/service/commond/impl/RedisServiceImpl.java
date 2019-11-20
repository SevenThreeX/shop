package com.sidianzhong.sdz.service.commond.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sidianzhong.sdz.model.ShopUser;
import com.sidianzhong.sdz.model.UserRole;
import com.sidianzhong.sdz.model.commond.PhoneCode;
import com.sidianzhong.sdz.model.commond.UserToken;
import com.sidianzhong.sdz.service.ShopUserService;
import com.sidianzhong.sdz.service.UserRoleService;
import com.sidianzhong.sdz.service.commond.RedisService;
import com.sidianzhong.sdz.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisServiceImpl implements RedisService {

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    ShopUserService userService;
    @Autowired
    UserRoleService userRoleService;

    @Resource
    private RedisTemplate<String, String> redis;

    private String secret = "seventhree";

    @Override
    public UserToken createToken(ShopUser user) {


        //        String token = UUID.randomUUID().toString().replace("-", "");
        String token = createUserToken(user);
        UserToken userToken = new UserToken();

        userToken.setUserId(user.getId());
        userToken.setToken(token);
//        存储到redis并设置过期时间
        redis.boundValueOps(user.getId().toString()).set(token, 72, TimeUnit.HOURS);
        return userToken;
    }

    @Override
    public UserToken getToken(Integer userId) {
        UserToken userToken = new UserToken();
        boolean exists = redis.hasKey(userId.toString());
        if (exists) {
            String token = redis.opsForValue().get(userId.toString());
            userToken.setUserId(userId);
            userToken.setToken(token);
        }
        return userToken;
    }


    //JWT 生成token
    public String createUserToken(ShopUser user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Map<String, Object> map = new HashMap<String, Object>();
            Date nowDate = new Date();
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            String token = JWT.create()
                    /*设置头部信息 Header*/
                    .withHeader(map)
                    /*设置 载荷 Payload*/
                    .withClaim("userId", user.getId().toString())
                    .withClaim("token", Tools.getUUID())
                    .withIssuedAt(nowDate) //生成签名的时间
                    // /*签名 Signature */
                    .sign(algorithm);
            return token;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public DecodedJWT verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            jwt = verifier.verify(token);
            return jwt;
        } catch (Exception exception) {
            throw new TokenException("请重新登录");
        }
    }

    @Override
    public boolean checkUserToken(String token) {
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new TokenException("wu");
        }
        DecodedJWT decodedJWT = verifyToken(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        String userId = claims.get("userId").asString();
        ShopUser user = userService.get(Integer.valueOf(userId));
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        request.setAttribute("USER_ID", Integer.valueOf(userId));
        redis.boundValueOps(userId).expire(72, TimeUnit.HOURS);
        return true;
    }

    @Override
    public boolean checkSellerToken(String token) {
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new TokenException("无token");
        }
        DecodedJWT decodedJWT = verifyToken(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        String userId = claims.get("userId").asString();
        ShopUser user = userService.get(Integer.valueOf(userId));
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        List<UserRole> userByUserId = userRoleService.getUserByUserId(Integer.valueOf(userId));
        for (UserRole userRole : userByUserId) {
            Integer roleId = userRole.getRoleId();
            //判断是否是有权限操作后台
            if (roleId == 0) {
                throw new PermissionException("无权限");
            }
        }

        request.setAttribute("USER_ID", Integer.valueOf(userId));
        redis.boundValueOps(userId).expire(72, TimeUnit.HOURS);
        return true;
    }

    @Override
    public boolean checkBackToken(String token) {
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new TokenException("无token");
        }
        DecodedJWT decodedJWT = verifyToken(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        String userId = claims.get("userId").asString();
        ShopUser user = userService.get(Integer.valueOf(userId));
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        List<UserRole> userByUserId = userRoleService.getUserByUserId(Integer.valueOf(userId));
        for (UserRole userRole : userByUserId) {
            Integer roleId = userRole.getRoleId();
            //判断是否是有权限操作后台
            if (roleId != 4) {
                throw new PermissionException("无权限");
            }
        }


        request.setAttribute("USER_ID", Integer.valueOf(userId));
        redis.boundValueOps(userId).expire(72, TimeUnit.HOURS);
        return true;
    }

    @Override
    public PhoneCode createCode(String phone, String code) {
        PhoneCode phoneCode = new PhoneCode();
        phoneCode.setPhone(phone);
        phoneCode.setCurrentTime(System.currentTimeMillis());
        phoneCode.setCode(code);
        redis.boundValueOps(phone).set(code, 5, TimeUnit.MINUTES);
        return phoneCode;
    }

    @Override
    public PhoneCode getCode(String phone) {
        PhoneCode phoneCode = new PhoneCode();
        boolean exists = redis.hasKey(phone);
        if (exists) {
            String code = redis.opsForValue().get(phone);
            phoneCode.setPhone(phone);
            phoneCode.setCurrentTime(System.currentTimeMillis());
            phoneCode.setCode(code);
        }
        return phoneCode;
    }

    @Override
    public Boolean deleteCode(String phone) {
        boolean exists = redis.hasKey(phone);
        Boolean delete = false;
        if (exists) {
            delete = redis.delete(phone);
        }
        return delete;
    }

    /**
     *          * 返回一定时间后的日期
     *          * @param date 开始计时的时间
     *          * @param year 增加的年
     *          * @param month 增加的月
     *          * @param day 增加的日
     *          * @param hour 增加的小时
     *          * @param minute 增加的分钟
     *          * @param second 增加的秒
     *          * @return
     *         
     */
    public Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        if (date == null) {
            date = new Date();
        }

        Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
        if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        if (day != 0) {
            cal.add(Calendar.DATE, day);
        }
        if (hour != 0) {
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }
        if (second != 0) {
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }

}
