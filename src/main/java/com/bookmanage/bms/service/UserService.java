package com.bookmanage.bms.service;

import com.bookmanage.bms.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    // 用户登录
    User login(User user);
    // 保存用户
    void saveUser(String token, User user);
    // 获取用户
    User getUser(String token);

    //移除用户
    void removeUser(String token);


    Integer register(String username, String password);

    //设置密码
    void setPassword(Integer id, String password);

    Integer getCount();

    //查询所有用户
    List<User> queryUsers();

    int getSearchCount(Map<String, Object> searchParam);

    //分页搜索用户
    List<User> searchUsersByPage(Map<String, Object> params);

    //添加用户
    Integer addUser(User user);

    //删除用户
    Integer deleteUser(User user);

    //批量删除用户
    Integer deleteUsers(List<User> users);

    //修改用户信息
    Integer updateUser(User user);


}
