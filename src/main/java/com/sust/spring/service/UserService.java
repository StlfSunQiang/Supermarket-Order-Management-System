package com.sust.spring.service;

import com.sust.spring.domain.User;

import java.util.List;

public interface UserService {
    //通过用户名及密码核查用户登录
    User checkLogin(String userName,String Password);

    //查询全部的用户信息
    List<User> findAllUser();

    //根据用户名和用户角色查询用户信息
    List<User> getUserList(String userName,String userRole);

    //根据id查询用户信息
    User getUserById(int id);

    //新增时，判断用户是否已经存在
    User ucexist(String userCode);

    //新增用户
    void addUser(User user);

    //根据id修改用户信息
    void doUserModify(User user);

    //修改密码
    Boolean doPwdModify(int id,String newPassword);

    //删除用户
    Boolean delUserById(int id);
}
