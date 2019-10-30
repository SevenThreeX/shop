package com.sidianzhong.sdz.pay.wxPay;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

@Service
@Transactional
public class WeiChartUtil {

    /**
     * 返回状态码
     */
    public final String ReturnCode = "return_code";

    /**
     * 返回信息
     */
    public final String ReturnMsg = "return_msg";

    /**
     * 业务结果
     */
    public final String ResultCode = "result_code";

    /**
     * 预支付交易会话标识
     */
    public final String PrepayId = "prepay_id";

    @Autowired
    private Prop prop;

    /**
     * 得到微信预付单
     *
     * @param orderCode 商户自己的订单号
     * @param totalFee  总金额  （元）
     * @return
     */
    public Map<String, String> getPreyId(
            String orderCode,
            Integer totalFee,
            String goodsDescribe) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("appid", WeChatAppConfig.AppId);
        reqMap.put("mch_id", WeChatAppConfig.MchId);
        reqMap.put("nonce_str", getRandomString());
        reqMap.put("body", "Moss财经-" + goodsDescribe);
        reqMap.put("out_trade_no", orderCode); //商户系统内部的订单号,
        reqMap.put("total_fee", String.valueOf(totalFee)); //订单总金额，单位为分
        reqMap.put("spbill_create_ip", getHostIp()); //用户端实际ip
        reqMap.put("fee_type", "CNY"); //默认人民币：CNY
        reqMap.put("notify_url", prop.getNotify_url()); //通知地址
        reqMap.put("trade_type", "APP"); //交易类型
        reqMap.put("sign", getSign(reqMap));

        String reqStr = creatXml(reqMap);
        String retStr = HttpClientUtil.postHttps(WeChatAppConfig.PrepayUrl, reqStr);
        return getInfoByXml(retStr);
    }


    public EnterpriceToCustomer withdraw(
            String openId,
            Integer money
    ) {
        Map<String, String> map = new HashMap<>();
        map.put("mch_appid", WeChatAppConfig.AppId);
        map.put("mchid", WeChatAppConfig.MchId);
        map.put("nonce_str", getRandomString());
        map.put("partner_trade_no", OrderCodeFactory.getWithdrawalCode(Long.valueOf(money)));
        map.put("openid", openId);
        map.put("check_name", "NO_CHECK");
        map.put("amount", money.toString());
        map.put("desc", "商户提现");
        map.put("spbill_create_ip", getHostIp());
        map.put("re_user_name", "用户-" + openId);
        map.put("sign", getSign(map));
        String document = creatXml(map);
        try {
            String withdrawal = HttpClientUtil.doWithdrawal(WeChatAppConfig.withdrawUrl, document, WeChatAppConfig.refund_file_path, WeChatAppConfig.MchId);
            EnterpriceToCustomer enterpriceToCustomer = parseXmlToMapEnterpriceToCustomer(withdrawal);
            System.out.println(enterpriceToCustomer);
            return enterpriceToCustomer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 下面是需要通过跟节点，找找到对应的类属性，手动把它set进去。因此API返回的参数不一样。需要写每个返回的Bean。
     * 解析企业支付申请
     * 解析的时候自动去掉CDMA
     *
     * @param xml
     */
    private EnterpriceToCustomer parseXmlToMapEnterpriceToCustomer(String xml) {
        EnterpriceToCustomer enterpriceToCustomer = null;
        try {
            StringReader read = new StringReader(xml);
            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            // 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            // 通过输入源构造一个Document
            org.jdom.Document doc;
            doc = sb.build(source);

            org.jdom.Element root = doc.getRootElement();// 指向根节点
            List<org.jdom.Element> list = root.getChildren();
            if (list != null && list.size() > 0) {
                enterpriceToCustomer = new EnterpriceToCustomer();
                for (org.jdom.Element element : list) {
                    System.out.println("key是：" + element.getName() + "，值是：" + element.getText());
                    if ("return_code".equals(element.getName())) {
                        enterpriceToCustomer.setReturn_code(element.getText());
                    }
                    if ("return_msg".equals(element.getName())) {
                        enterpriceToCustomer.setReturn_msg(element.getText());
                    }

                    if ("mchid".equals(element.getName())) {
                        enterpriceToCustomer.setMchid(element.getText());
                    }
                    if ("nonce_str".equals(element.getName())) {
                        enterpriceToCustomer.setNonce_str(element.getText());
                    }
                    if ("result_code".equals(element.getName())) {
                        enterpriceToCustomer.setResult_code(element.getText());
                    }
                    if ("partner_trade_no".equals(element.getName())) {
                        enterpriceToCustomer.setPartner_trade_no(element.getText());
                    }
                    if ("payment_no".equals(element.getName())) {
                        enterpriceToCustomer.setPayment_no(element.getText());
                    }
                    if ("payment_time".equals(element.getName())) {
                        enterpriceToCustomer.setPayment_time(element.getText());
                    }
                    if ("err_code".equals(element.getName())) {
                        enterpriceToCustomer.setErr_code(element.getText());
                    }
                    if ("err_code_des".equals(element.getName())) {
                        enterpriceToCustomer.setErr_code_des(element.getText());
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return enterpriceToCustomer;
    }

    /**
     * 关闭订单
     *
     * @param orderId 商户自己的订单号
     * @return
     */
    public Map<String, String> closeOrder(String orderId) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("appid", WeChatAppConfig.AppId);
        reqMap.put("mch_id", WeChatAppConfig.MchId);
        reqMap.put("nonce_str", getRandomString());
        reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
        reqMap.put("sign", getSign(reqMap));

        String reqStr = creatXml(reqMap);
        String retStr = HttpClientUtil.postHttps(WeChatAppConfig.CloseOrderUrl, reqStr);
        return getInfoByXml(retStr);
    }


    /**
     * 查询订单
     *
     * @param orderId 商户自己的订单号
     * @return
     */
    public Map<String, String> getOrder(String orderId) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("appid", WeChatAppConfig.AppId);
        reqMap.put("mch_id", WeChatAppConfig.MchId);
        reqMap.put("nonce_str", getRandomString());
        reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
        reqMap.put("sign", getSign(reqMap));

        String reqStr = creatXml(reqMap);
        String retStr = HttpClientUtil.postHttps(WeChatAppConfig.OrderUrl, reqStr);
        //return retStr;
        return getInfoByXml(retStr);
    }


    /**
     * 退款
     *
     * @param orderId   商户订单号
     * @param totalFee  总金额（分）
     * @param refundFee 退款金额（分）
     */
    public Map<String, String> refundWei(String orderId, Integer totalFee, Integer refundFee, String refund_desc) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("appid", WeChatAppConfig.AppId);
        reqMap.put("mch_id", WeChatAppConfig.MchId);
        reqMap.put("nonce_str", getRandomString());
        reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
        reqMap.put("out_refund_no", OrderCodeFactory.getRefundCode(Long.valueOf(orderId))); //商户退款单号
        reqMap.put("total_fee", String.valueOf(totalFee)); //总金额
        reqMap.put("refund_fee", String.valueOf(refundFee)); //退款金额
        reqMap.put("refund_desc", refund_desc); //退款原因
        reqMap.put("notify_url", prop.getRefund_notify_url()); //退款反馈url
        reqMap.put("sign", getSign(reqMap));
        String reqStr = creatXml(reqMap);
        String retStr;
        try {
            retStr = HttpClientUtil.postHttpClientNeedSSL(WeChatAppConfig.RefundUrl, reqStr, WeChatAppConfig.refund_file_path, WeChatAppConfig.MchId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return getInfoByXml(retStr);
    }


    /**
     * 退款查询
     *
     * @param refundId 退款单号
     * @return
     */
    public Map<String, String> getRefundWeiInfo(String refundId) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("appid", WeChatAppConfig.AppId);
        reqMap.put("mch_id", WeChatAppConfig.MchId);
        reqMap.put("nonce_str", getRandomString());
        reqMap.put("out_refund_no", refundId); //商户退款单号
        reqMap.put("sign", getSign(reqMap));

        String reqStr = creatXml(reqMap);
        String retStr = HttpClientUtil.postHttps(WeChatAppConfig.RefundQueryUrl, reqStr);
        return getInfoByXml(retStr);
    }

    /**
     * 这个方法 可以自己写，以前我使用的是我公司封装的类，后来很多人找我要JAR包，所以我改成了这样，方便部分人直接使用代码，我自己未测试，不过应该问题不大，欢迎使用有问题的找我。
     * 传入map  生成头为XML的xml字符串，例：<xml><key>123</key></xml>
     *
     * @param reqMap
     * @return
     */
    public String creatXml(Map<String, String> reqMap) {
        Set<String> set = reqMap.keySet();
        StringBuffer b = new StringBuffer();
        b.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        b.append("<xml>");
        for (String key : set) {
            b.append("<" + key + ">").append(reqMap.get(key)).append("</" + key + ">");
        }
        b.append("</xml>");
        return b.toString();
    }

    /**
     * 得到加密值
     */
    public String getSign(Map<String, String> map) {
        String[] keys = map.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder reqStr = new StringBuilder();
        for (String key : keys) {
            String v = map.get(key);
            if (v != null && !v.equals("")) {
                reqStr.append(key).append("=").append(v).append("&");
            }
        }
        reqStr.append("key").append("=").append(WeChatAppConfig.bizAppSecret);

        return WeiMd5.encode(reqStr.toString()).toUpperCase();
    }

    /**
     * 得到10 位的时间戳
     * 如果在JAVA上转换为时间要在后面补上三个0
     *
     * @return
     */
    public String getTenTimes() {
        String t = new Date().getTime() + "";
        t = t.substring(0, t.length() - 3);
        return t;
    }

    /**
     * 得到随机字符串
     */
    public String getRandomString() {
        int length = 32;
//        String str = "0123456789";
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);//[0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 得到本地机器的IP
     *
     * @return
     */
    private String getHostIp() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 将xml解析成map
     *
     * @param xmlStr
     * @return
     */
    public Map<String, String> getInfoByXml(String xmlStr) {
        try {
            Map<String, String> m = new HashMap<>();
            Document d = DocumentHelper.parseText(xmlStr);
            Element root = d.getRootElement();
            for (Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
                Element element = (Element) i.next();
                String name = element.getName();
                if (!element.isTextOnly()) {
                    //不是字符串 跳过。确定了微信放回的xml只有根目录
                    continue;
                } else {
                    m.put(name, element.getTextTrim());
                }
            }
            //对返回结果做校验.去除sign 字段再去加密
            String retSign = m.get("sign");
            m.remove("sign");
            String rightSing = getSign(m);
            if (rightSing.equals(retSign)) {
                m.put("sign", retSign);
                return m;
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将金额转换成分
     *
     * @param fee 元格式的
     * @return 分
     */
    public String changeToFen(Double fee) {
        String priceStr = "";
        if (fee != null) {
            int p = (int) (fee * 100); //价格变为分
            priceStr = Integer.toString(p);
        }
        return priceStr;
    }


    /**
     * 接收微信的异步通知
     *
     * @throws IOException
     */
    public String reciverWx(HttpServletRequest request) throws IOException {
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        return sb.toString();
    }

    /**
     * 验证回调签名
     *
     * @return
     */
    public boolean isTenpaySign(Map<String, String> map) {
        String signFromAPIResponse = map.get("sign");
        if (signFromAPIResponse == null || signFromAPIResponse.equals("")) {
            LOGGER.error("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        LOGGER.info("服务器回包里面的签名是:" + signFromAPIResponse);
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        //算出签名
        String resultSign = "";
        map.remove("sign");
        String tobesign = getSign(map);
        try {
            resultSign = WeiMd5.encode(tobesign).toUpperCase();
        } catch (Exception e) {
            resultSign = WeiMd5.encode(tobesign).toUpperCase();
        }
        LOGGER.info("微信回调；再次验证签名正确与否生成的签名：{}", resultSign);
        String tenpaySign = signFromAPIResponse.toUpperCase();
        return tenpaySign.equals(resultSign);
    }

    private final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(WeiChartUtil.class);

}

