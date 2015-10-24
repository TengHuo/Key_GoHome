package com.huoteng;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.net.URL;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
/**
 * Created by teng on 10/21/15.
 * 发送车票查询请求
 */
public class TicketSpider {

    public static void main(String[] args) throws Exception{
        String test = sendSSLRequest("https://kyfw.12306.cn/otn/lcxxcx/query?purpose_codes=ADULT&queryDate=2015-10-26&from_station=SHH&to_station=BJP");
        System.out.println("RESEULT:" + test);
    }


    public static String sendSSLRequest(String requestUrl) {
        StringBuffer strBuff = new StringBuffer();
        //创建SSLContext对象,初始化信任管理器
        TrustManager[] trust = {new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {

            }

            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }
        }};
        SSLContext sslContext = null;
        BufferedReader reader = null;
        try {
            sslContext = SSLContext.getInstance("SSL","SunJSSE");
            sslContext.init(null, trust, new SecureRandom());
            //获得工厂
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            //构造HTTPS协议
            URL url = new URL(requestUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            //设置证书
            con.setSSLSocketFactory(ssf);
            //设置超时
            con.setReadTimeout(3000);
            con.setConnectTimeout(3000);

            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            //设置请求方法
            con.setRequestMethod("GET");
            //打开连接
            con.connect();
            //获得输入流,设置编码方式
            reader = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
            String temp = null;
            while((temp=reader.readLine())!=null){
                strBuff.append(temp);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                    reader = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strBuff.toString();
    }
}