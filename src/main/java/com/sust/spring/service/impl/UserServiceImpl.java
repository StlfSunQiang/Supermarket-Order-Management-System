package com.sust.spring.service.impl;

import com.sust.spring.domain.User;
import com.sust.spring.mapper.UserMapper;
import com.sust.spring.service.UserService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("userService")
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    //登录验证
    public User checkLogin(String userCode, String Password){
       //根据用户名实例化用户对象
        User user = userMapper.getUserByName(userCode);
        if (user != null && user.getUserPassword().equals(Password)){
            return user;
        }
        return null;
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public List<User> getUserList(String userName, String userRole) {
        return userMapper.getUserList(userName,userRole);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User ucexist(String userCode) {
        return userMapper.ucexist(userCode);
    }

    @Override
    public void addUser(User user) {
       userMapper.addUser(user);
    }

    @Override
    public void doUserModify(User user) {
        userMapper.doUserModify(user);
    }

    @Override
    public Boolean doPwdModify(int id, String newPassword) {
        return userMapper.doPwdModify(id,newPassword);
    }

    @Override
    public Boolean delUserById(int id) {
        return userMapper.delUserById(id);
    }
}
