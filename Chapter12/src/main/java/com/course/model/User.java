package com.course.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String userName;
    private String psssword;
    private int age;
    private int sex;
    private int permission;
    private int isDelete;

    @Override //复写toSting，将对象转换为json格式
    public String toString() {
        return ( "{" +
                "id" + id + "," +
                "userName" + userName + "," +
                "psssword" + psssword + "," +
                "age" + age + "," +
                "sex" + sex + "," +
                "permission" + permission + "," +
                "isDelete" + isDelete + "}"
                );
    }
}
