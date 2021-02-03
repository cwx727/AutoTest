package com.course.httpclient.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesParamGet {
    private String url;
    private ResourceBundle bundle;
    private CookieStore store;


    @BeforeTest
    public void beforeTest() {
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test //获得cookies信息
    public void Getcookies() throws IOException {
        String uri = bundle.getString("getcookies.uri");
        uri = this.url + uri;
        HttpGet get = new HttpGet(uri);
        //HttpClient client = HttpClientBuilder.create().build();
       // CookieStore store = new BasicCookieStore();  //创建一个cookiestroe
        this.store =  new BasicCookieStore();
        CloseableHttpClient Client = HttpClients.custom().setDefaultCookieStore(this.store).build();//创建带cookie的client
        HttpResponse response = Client.execute(get);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("getcookie-----"+result);


        List<Cookie> cookiesList = this.store.getCookies();
        System.out.println("store------" + this.store);
        System.out.println("cookiesList-------" + cookiesList);
        for (Cookie cookie : cookiesList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name = " + name + "; value = " + value);
        }
    }

    @Test(dependsOnMethods = {"Getcookies"})
    public void setCookies() throws IOException {
        String uri = bundle.getString("setcookies.uri");
        url = this.url + uri;
        HttpGet get = new HttpGet(url);
        CloseableHttpClient Client = HttpClients.custom().setDefaultCookieStore(this.store).build();
        HttpResponse response = Client.execute(get);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("setcook-----" + result);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode-------"+statusCode);
    }

}
