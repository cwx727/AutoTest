package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class LoginTest {
    @BeforeTest(groups = "loginTrue", description = "测试准备工作,获取httpclient对象")
    public void beforeTest() {
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

        //TestConfig.store = new BasicCookieStore();
        //TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.store).build();
    }

    @Test(groups = "loginTrue", description = "用户登陆成功")
    public void loginTrue() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",1); //取数据库loginCase测试案例表中的id为1的数据
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //将loginCase表的值传入getResult方法中，取到相应param发起post请求
        String result = getResult(loginCase);
        System.out.println("result--"+result);
        //判断测试案例中的预期结果和请求结果中在的返回值是否一致，一致通过，不一致失败
        Assert.assertEquals(loginCase.getExpected(),result);
    }



    @Test(groups ="loginFalse", description = "用户的登陆失败")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        String result = getResult(loginCase);
        System.out.println("result"+result);
        Assert.assertEquals(loginCase.getExpected(),result);
    }
    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.loginUrl);
        //System.out.println(TestConfig.loginUrl);
        post.setHeader("Content-Type","application/json");
        //定义param为json格式
        JSONObject param=new JSONObject();
        //将测试案例中的用户名和密码传入到param中
        param.put("userName",loginCase.getUserName());
        param.put("password",loginCase.getPassword());
        System.out.println("param--"+param.toString());
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        System.out.println("entity----"+entity);
        post.setEntity(entity);
        System.out.println("post---"+post);

        //定义cookiestore，发起请求
        TestConfig.store = new BasicCookieStore();
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.store).build();
        //TestConfig.httpClient = HttpClients.custom().build();
        //发起post请求
        HttpResponse response = TestConfig.httpClient.execute(post);
        List<Cookie> cookies = TestConfig.store.getCookies(); //获得cookiesList

        System.out.println("cookies--"+cookies);
        String result= EntityUtils.toString(response.getEntity(),"utf-8");
        return result;  //login接口响应返回值，登陆成功返回true 登陆失败返回false
    }
}
