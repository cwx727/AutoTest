package com.course.model;

import lombok.Data;

@Data
public class AddUserCase {
    public int id;
    public String userName;
    public String password;
    public int age;
    public int sex;
    public int permission;
    public int isDelete;
    public String expected;
}
