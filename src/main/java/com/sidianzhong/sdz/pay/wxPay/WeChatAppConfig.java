package com.sidianzhong.sdz.pay.wxPay;

import java.io.File;

public class WeChatAppConfig {

    /**
     * 预支付请求地址
     */
    public static final String PrepayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 查询订单地址
     */
    public static final String OrderUrl = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 关闭订单地址
     */
    public static final String CloseOrderUrl = "https://api.mch.weixin.qq.com/pay/closeorder";

    /**
     * 申请退款地址
     */
    public static final String RefundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 查询退款地址
     */
    public static final String RefundQueryUrl = "https://api.mch.weixin.qq.com/pay/refundquery";

    /**
     * 从企业进行提现
     */
    public static final String withdrawUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";



    /**
     * 下载账单地址
     */
    public static final String DownloadBillUrl = "https://api.mch.weixin.qq.com/pay/downloadbill";

    /**
     * 商户APPID
     */
    public static final String AppId = "wx7978bfabe3198b78";

    /**
     * 商户账户 获取支付能力后，从邮件中得到
     */
    public static final String MchId = "1524149151";

    /**
     * 商户秘钥  32位，在微信商户平台中设置 api安全下的设置密钥(不是开放平台 )  注意！！！ 注意！！！！
     */
    public static final String bizAppSecret = "39nsk902jfdkfd930jjfjkdk30jslxaa";



    public static final String openPlatformAppSecret = "ebf0f533cc414cd12dac81c1780cf305";

    /**
     * sign type
     */
    public static final String Sign = "MD5";


    /**
     * 退款需要证书文件，证书文件的路径
     */
    public static String refund_file_path = "wx" + File.separator + "wx_pay_cert.p12";

    /**
     * 证书密码
     */
    public static String refund_file_secret = MchId;//未更改则是默认的商户ID


}

