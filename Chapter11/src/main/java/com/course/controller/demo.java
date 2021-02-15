package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
@Api(value = "v1")
@RequestMapping("v1")
public class demo {
    @Autowired //启动即加载
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount", method = RequestMethod.GET)
    @ApiOperation(value = "用户数",httpMethod = "GET")
    public int getUserCount() {

        return template.selectOne("getUserCount");
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户", httpMethod = "POST")
    public int addUser(@RequestBody User user) {
        return template.insert("addUser",user);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户", httpMethod = "POST")
    public int updateUser(@RequestBody User user) {
        return template.update("updateUser",user);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    @ApiOperation("删除用户")
    public int deleteUser(@RequestParam int id) {
        return template.delete("deleteUser",id);
    }
}
