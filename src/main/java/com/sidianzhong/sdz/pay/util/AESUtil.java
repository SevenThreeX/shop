package com.sidianzhong.sdz.pay.util;

import com.sidianzhong.sdz.pay.wxPay.WeiMd5;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AESUtil {
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";


    /**
     * AES加密
     *
     * @param data
     */
    public static String encryptData(String appSecret, String data) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(WeiMd5.encode(appSecret).toLowerCase().getBytes(), ALGORITHM));
        return Base64Util.encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     */
    public static String decryptData(String appSecret, String base64Data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(WeiMd5.encode(appSecret).toLowerCase().getBytes(), ALGORITHM));
        return new String(cipher.doFinal(Base64Util.decode(base64Data)));
    }
}
