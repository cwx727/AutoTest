package com.course.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class DatabaseUtil {
    public static SqlSession getSqlSession() throws IOException {
        Reader reader = Resources.getResourceAsReader("databaseConfig.xml");  //读取database配置文件
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader); //加载database配置文件
        SqlSession sqlsession = factory.openSession(); //执行database配置文件中的sql语句
        return sqlsession;
    }
}
