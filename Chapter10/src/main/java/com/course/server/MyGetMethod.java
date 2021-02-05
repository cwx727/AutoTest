package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@Api("/")
public class MyGetMethod {
    //给客户端返回cookies接口
    @RequestMapping(value = "/getCookies", method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获得cookies", httpMethod = "GET")
    public String getCookies(HttpServletResponse response) {
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        return "cookies返回成功";
    }

    //要求客户端必须携带cookies访问
    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value = "要求客户端必须携带cookies访问", httpMethod = "GET")
    public String getwithcookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return "未携带cookies";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")) {
                return "客户端必须携带cookies访问 成功";
            }
        }
        return "0";
    }

    //带参数的get请求
    //url:   http://localhost:8888/get/with/param?start=1&end=2
    @RequestMapping(value = "/get/with/param", method = RequestMethod.GET)
    @ApiOperation(value = "带参数的get请求?start=1&end=2", httpMethod = "GET")
    public Map<String, Integer> getList1 (@RequestParam Integer start,
                                         @RequestParam Integer end) {
        Map<String, Integer> myList = new HashMap<>();
        myList.put("商品1", 300);
        myList.put("商品2", 100);
        myList.put("商品3", 200);

        return myList;
    }

    //带参数的get请求
    //url:   http://localhost:8888/get/with/param/1/2
    @RequestMapping(value = "/get/with/param/{start}/{end}")
    @ApiOperation(value = "带参数的get请求?/1/2", httpMethod = "GET")
    public Map<String, Integer> getList2 (@PathVariable Integer start,
                                         @PathVariable Integer end) {
        Map<String, Integer> myList = new HashMap<>();
        myList.put("商品1", 300);
        myList.put("商品2", 100);
        myList.put("商品3", 200);

        return myList;
    }
}



