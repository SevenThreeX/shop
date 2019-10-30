package com.sidianzhong.sdz.utils;


import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;


public class SendPhoneMessage {


	/**
     * @param
     * @throws IOException
     * @throws HttpException
     */
	   public static int sendMsg(String phone,String code) throws HttpException, IOException {
        HttpClient client=new HttpClient();
        PostMethod post=new PostMethod("http://gbk.sms.webchinese.cn");
        //��ͷ�ļ�������ת��
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");
        NameValuePair[] data={
                new NameValuePair("Uid","shopApp"),
                new NameValuePair("Key","230706af1a4ce0cde74a"),
                new NameValuePair("smsMob",phone),
                new NameValuePair("smsText","验证码:"+code)
        };
        post.setRequestBody(data);
        client.executeMethod(post);
        Header[] headers=post.getRequestHeaders();
        int statusCode=post.getStatusCode();
        System.out.println("statusCode:"+statusCode);
        for(Header h:headers){
            System.out.println(h.toString());
        }
        String result=new String(post.getResponseBodyAsString().getBytes("gbk"));
        System.out.println(result);
        post.releaseConnection();
        return statusCode;
    }
}