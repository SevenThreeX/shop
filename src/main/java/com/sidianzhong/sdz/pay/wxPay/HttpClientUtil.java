package com.sidianzhong.sdz.pay.wxPay;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;

public class HttpClientUtil {

    public static String post(String url, Map<String, String> headMap, Map<String, String> params) {
        try {
            HttpClient httpclient = new HttpClient();
            httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            PostMethod httpPost = new PostMethod(url);
            if (null != headMap) {
                for (String key : headMap.keySet()) {
                    httpPost.setRequestHeader(key, headMap.get(key));
                }
            }

            if (null != params) {
                for (String pkey : params.keySet()) {
                    httpPost.addParameter(pkey, params.get(pkey));
                }
            }
            httpclient.executeMethod(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpPost.getResponseBodyAsStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            reader.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postHttplient(String url, String xmlInfo) {
        try {
            HttpClient httpclient = new HttpClient();
            httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            PostMethod httpPost = new PostMethod(url);
            httpPost.setRequestEntity(new StringRequestEntity(xmlInfo));
            httpclient.executeMethod(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpPost.getResponseBodyAsStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            reader.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String doWithdrawal(String url, String xmlData, String cretPath, String mrchId) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (InputStream inputStream = new ClassPathResource(cretPath).getInputStream()) {
            keyStore.load(inputStream, WeChatAppConfig.MchId.toCharArray());//这里写密码..默认是你的MCHID 证书密码
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, WeChatAppConfig.refund_file_secret.toCharArray())//这里也是写密码的
                .build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build()) {
            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(xmlData, "UTF-8"));
            try (CloseableHttpResponse response = httpclient.execute(httpost)) {
                HttpEntity entity = response.getEntity();
                String returnMessage = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return returnMessage; //返回后自己解析结果
            }
        }
    }


    /**
     * 需要加密执行的
     *
     * @param url
     * @param xmlInfo
     * @return
     * @throws Exception
     */
    public static String postHttpClientNeedSSL(String url, String xmlInfo, String cretPath, String mrchId)
            throws Exception {
        // 选择初始化密钥文件格式
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // 得到密钥文件流
        try (InputStream inputStream = new ClassPathResource(cretPath).getInputStream()) {
            // 用商户的ID 来解读文件
            keyStore.load(inputStream,  WeChatAppConfig.refund_file_secret.toCharArray());
        }
        // 用商户秘钥进行加载    默认是商户ID
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WeChatAppConfig.MchId.toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        // 用最新的httpclient 加载密钥
        StringBuilder ret = new StringBuilder();
        try (CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build()) {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(xmlInfo));
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        ret.append(text);
                    }
                }
                EntityUtils.consume(entity);
            }
        }
        return ret.toString();
    }

    public static String postHttps(String urlStr, String xmlInfo) {
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "utf-8");
            out.write(xmlInfo);
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder lines = new StringBuilder();
            String line;
            for (line = br.readLine(); line != null; line = br.readLine()) {
                lines.append(line);
            }
            return lines.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

