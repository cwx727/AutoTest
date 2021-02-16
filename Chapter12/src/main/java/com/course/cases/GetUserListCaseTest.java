package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
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
import java.util.List;

public class GetUserListCaseTest {
    @Test(dependsOnGroups = "loginTrue", description = "获得性别为男用户信息")
    public void getUserList() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);

        JSONArray resultJson = getJsonResult(getUserListCase);
        List<User> userList = session.selectList(getUserListCase.getExpected(),getUserListCase);
        for(User user:userList) {
            System.out.println("获取到的user：" + user.toString());
        }
        JSONArray userListJson = new JSONArray(userList); //将jsonlist转换成jsonarray
        Assert.assertEquals(userListJson.length(),resultJson.length());//判断长度是否一致
        //判断元素是否一致
        for(int i=0; i<resultJson.length(); i++) {
            JSONObject expect = (JSONObject) resultJson.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(),actual.toString());
        }
    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.getUserListUrl);
        post.setHeader("Content-Type","application/json");
        JSONObject param=new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("age",getUserListCase.getAge());
        param.put("sex",getUserListCase.getSex());
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.store).build();
        //发起post请求
        HttpResponse response = TestConfig.httpClient.execute(post);
        String result= EntityUtils.toString(response.getEntity());
        System.out.println(result);
        JSONArray jsonArray = new JSONArray(result);
        return jsonArray;
    }
}
