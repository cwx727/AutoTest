package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.InterfaceName;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoCaseTest {
    @Test(dependsOnGroups = "loginTrue", description = "获得userid为1的用户信息")
    public void getUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase", 1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        JSONArray resultJson = getJsonList(getUserInfoCase);

        User user = session.selectOne(getUserInfoCase.getExpected(), getUserInfoCase);
        System.out.println("user.toString()"+user.toString());
        //将查询到的值转换成JsonArray
        List userList = new ArrayList();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        System.out.println("jsonArray---"+jsonArray);

        //判断元素是否一致
        JSONArray resultJson1 = new JSONArray(resultJson.getString(0));

        System.out.println("resultJson1---" +resultJson1);
        //System.out.println("resultJson--" + resultJson);

        //JSONArray jsonArray1 = new JSONArray(jsonArray.getString(0));

        Assert.assertEquals(jsonArray.toString(),resultJson1.toString());

    }

    private JSONArray getJsonList(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.getUserInfoUrl);
        post.setHeader("Content-Type","application/json");
        JSONObject param=new JSONObject();
        param.put("id",getUserInfoCase.getUserid());
        System.out.println("param.toString()-----"+param.toString());
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.store).build();
        //发起post请求
        HttpResponse response = TestConfig.httpClient.execute(post);
        String result= EntityUtils.toString(response.getEntity());

        System.out.println("result----"+result);
        List resultList = Arrays.asList(result);//将result转换成list
        JSONArray jsonArray = new JSONArray(resultList);
        //System.out.println("result--jsonArray---"+jsonArray);
        return jsonArray;
    }
}
