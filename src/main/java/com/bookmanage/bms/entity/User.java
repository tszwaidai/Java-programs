package com.bookmanage.bms.entity;

import lombok.Data;

import java.io.Serializable;

@Data
//序列化 便于网络传输
public class User implements Serializable {
    private Integer userid;
    private String username;
    private String userpassword;
    private Byte isadmin; //判断是否为管理员


    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserpassword() {
        return userpassword;
    }
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
    public Byte getIsadmin() {
        return isadmin;
    }
    public void setIsadmin(Byte isadmin) {
        this.isadmin = isadmin;
    }


}