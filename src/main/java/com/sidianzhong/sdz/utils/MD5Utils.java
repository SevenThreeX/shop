package com.sidianzhong.sdz.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description:主要功能:MD5加密 不可逆（Message Digest，消息摘要算法）
 * @Prject: CommonUtilLibrary
 * @Package: com.jingewenku.abrahamcaijin.commonutil.encryption
 * @author: AbrahamCaiJin
 * @date: 2017年05月16日 15:56
 * @Copyright: 个人版权所有
 * @Company:
 * @version: 1.0.0
 */

public class MD5Utils {

    private MD5Utils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * MD5加密
     */
    public static String encryptMD5(String securityStr) {
        byte[] data = securityStr.getBytes();
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] resultBytes = md5.digest();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < resultBytes.length; i++) {
            if (Integer.toHexString(0xFF & resultBytes[i]).length() == 1) {
                builder.append("0").append(
                    Integer.toHexString(0xFF & resultBytes[i]));
            } else {
                builder.append(Integer.toHexString(0xFF & resultBytes[i]));
            }
        }
        return builder.toString();
    }


    /**
     * 校验字符串的md5值
     *
     * @param str 目标字符串
     * @param md5 基准md5
     * @return 校验结果
     */
    public static boolean checkMD5(String str, String md5) {
        return encryptMD5(str).equalsIgnoreCase(md5);
    }

    /**
     * 获得加盐md5，算法过程是原字符串md5后连接加盐字符串后再进行md5
     *
     * @param str  待加密的字符串
     * @param salt 盐
     * @return 加盐md5
     */
    public static String getMD5AndSalt(String str, String salt) {
        return encryptMD5(encryptMD5(str).concat(salt));
    }

}