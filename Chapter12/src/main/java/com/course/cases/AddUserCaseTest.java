package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddUserCaseTest {
    @Test(dependsOnGroups = "loginTrue", description = "添加用户接口测试")
    public void addUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase",1);//取到测试案例数据
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //发送请求
        String result = gerResult(addUserCase);//传入测试案例数据发送请求
        //等待http请求完成
        Thread.sleep(2000);
        User user = session.selectOne("addUser", addUserCase);
        //System.out.println("user.toString()"+user.toString());
        Assert.assertEquals(addUserCase.getExpected(), result);
    }

    private String gerResult(AddUserCase addUserCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.addUserUrl);
        post.setHeader("Content-Type","application/json");
        JSONObject param=new JSONObject();
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("age",addUserCase.getAge());
        param.put("sex",addUserCase.getSex());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete", addUserCase.getIsDelete());
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.store).build();
        //发起post请求
        HttpResponse response = TestConfig.httpClient.execute(post);
        String result= EntityUtils.toString(response.getEntity());
        System.out.println("result----"+result);
        return result;
    }
}
