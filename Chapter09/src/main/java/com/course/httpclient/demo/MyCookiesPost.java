package com.course.httpclient.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesPost {
    private String url;
    private ResourceBundle bundle;
    private CookieStore store;


    @BeforeTest
    public void beforeTest() {
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test //获得cookies信息
    public void getCookies() throws IOException {
        String uri = bundle.getString("getcookies.uri");
        uri = this.url + uri;
        HttpGet get = new HttpGet(uri);
        //HttpClient client = HttpClientBuilder.create().build();
        // CookieStore store = new BasicCookieStore();  //创建一个cookiestroe
        this.store = new BasicCookieStore();
        CloseableHttpClient Client = HttpClients.custom().setDefaultCookieStore(this.store).build();//创建带cookie的client
        HttpResponse response = Client.execute(get);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println("getcookie-----" + result);


        List<Cookie> cookiesList = this.store.getCookies();
        System.out.println("store------" + this.store);
        System.out.println("cookiesList-------" + cookiesList);
        for (Cookie cookie : cookiesList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name = " + name + "; value = " + value);
        }
    }

    @Test(dependsOnMethods = {"getCookies"})
    public void postCookies() throws IOException {
        String uri = this.url + bundle.getString("postcookies.uri");
        HttpPost post=new HttpPost(uri);  //post请求
        //添加header
        post.setHeader("Content-Type","application/json");
        //请求json赋值
        JSONObject param=new JSONObject();
        param.put("name","xiaoming");
        param.put("age","18");
        //将参数添加到方法中
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //创建client 并传入cookies
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(this.store).build();
        //发起post请求
        HttpResponse response = client.execute(post);
        //获得相应信息
        String result=EntityUtils.toString(response.getEntity());

        //处理响应结果，判断
        //将返回的响应结果字符串转化为json格式
        JSONObject resultJson = new JSONObject(result);
        //判断结果值
        String success = (String) resultJson.get("name");
        String status = (String) resultJson.get("status");
        Assert.assertEquals("success", success);
        Assert.assertEquals("1", status);

    }

}
