package com.sidianzhong.sdz.pay.wxPay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//提现返回参数
public class EnterpriceToCustomer {
    /*    <xml>
        <return_code><![CDATA[SUCCESS]]></return_code>
        <return_msg><![CDATA[]]></return_msg>
        <mchid><![CDATA[1488323162]]></mchid>
        <nonce_str><![CDATA[o9fcpfvqow1aks48a2omvayu1ne7c709]]></nonce_str>
        <result_code><![CDATA[SUCCESS]]></result_code>
        <partner_trade_no><![CDATA[xvuct0087w4t1dpr87iqj98w5f71ljae]]></partner_trade_no>
        <payment_no><![CDATA[1000018301201801163213961289]]></payment_no>
        <payment_time><![CDATA[2018-01-16 14:52:16]]></payment_time>
        </xml>
    */
    private String return_code;
    private String return_msg;
    private String mchid;
    private String nonce_str;
    private String result_code;
    private String partner_trade_no;
    private String payment_no;
    private String payment_time;

    /*
     * 支付错误时，返回的代码
     *  key是：return_code，值是：SUCCESS
        key是：return_msg，值是：支付失败
        key是：mch_appid，值是：wx49c22ad731b679c3
        key是：mchid，值是：1488323162
        key是：result_code，值是：FAIL
        key是：err_code，值是：AMOUNT_LIMIT
        key是：err_code_des，值是：付款金额超出限制。低于最小金额1.00元或累计超过20000.00元。
     *
     */
    private String err_code;

    private String err_code_des;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getPayment_no() {
        return payment_no;
    }

    public void setPayment_no(String payment_no) {
        this.payment_no = payment_no;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }
}
