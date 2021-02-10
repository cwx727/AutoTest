package com.course.utils;

import com.course.model.InterfaceName;

import java.util.ResourceBundle;

//测试请求url
public class ConfigFile {
    private static ResourceBundle bundle = ResourceBundle.getBundle("application");

    public static String getUrl(InterfaceName name) {
        String address = bundle.getString("test.url");
        String uri = "";
        if (name == InterfaceName.ADDUSER) {
            uri = bundle.getString("addUser.uri");
        }
        if (name == InterfaceName.GETUSERINFO) {
            uri = bundle.getString("getUserInfo.uri");
        }
        if (name == InterfaceName.GETUSERLIST) {
            uri = bundle.getString("getUserList.uri");
        }
        if (name == InterfaceName.LOGIN) {
            uri = bundle.getString("login.uri");
        }
        if (name == InterfaceName.UPDATEUSERINFO) {
            uri = bundle.getString("updateUserInfo.uri");
        }
        String url = address + uri;
        return url;
    }
}
