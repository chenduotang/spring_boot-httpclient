package com.cc.springhttpclient.controller;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class doGet {
    @ResponseBody
    @RequestMapping("/doGetControllerOne")
    public String doGetController(HttpServletRequest req, HttpServletResponse rep){
        String keywords=req.getParameter("keywords");
        if(StringUtils.isEmpty(keywords)){
            keywords="重庆";
        }
        System.out.println("省份为:"+keywords);
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求,里面需要放请求的
        HttpGet httpGet = new HttpGet("https://restapi.amap.com/v3/config/district?key=dbeffd2bd610553f9b381d85e50954af&keywords="+keywords+"&subdistrict=1&extensions=base");
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity  = response.getEntity();
            if (responseEntity != null) {
                //System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));第一次使用的时候会关闭流
                String result=EntityUtils.toString(responseEntity);//解析json数据
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
