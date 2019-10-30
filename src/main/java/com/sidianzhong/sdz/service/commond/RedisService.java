package com.sidianzhong.sdz.service.commond;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sidianzhong.sdz.model.ShopUser;
import com.sidianzhong.sdz.model.commond.PhoneCode;
import com.sidianzhong.sdz.model.commond.UserToken;

public interface RedisService {


    UserToken createToken(ShopUser User);

    UserToken getToken(Integer userId);

    DecodedJWT verifyToken(String token);

    boolean checkUserToken(String token);   //验证用户token

    boolean checkSellerToken(String token);   //验证商家token

    boolean checkBackToken(String token);   //验证后台token

    PhoneCode createCode(String phone,String code);

    PhoneCode getCode(String phone);

    Boolean deleteCode(String phone);


}
