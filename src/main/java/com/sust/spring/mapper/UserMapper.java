package com.sust.spring.mapper;

import com.sust.spring.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

     //根据用户编码获取用户(处理登录)
     User getUserByName(String userCode);

     //查询全部的用户信息
     List<User> findAllUser();

     //根据用户名和用户角色查询用户信息
     List<User> getUserList(@Param("userName") String userName, @Param("userRole") String userRole);

     //根据id查询用户信息
     User getUserById(int id);

     //新增时，判断用户是否已经存在
     User ucexist(String userCode);

     //新增用户
     void addUser(User user);

     //修改用户信息
     void doUserModify(User user);

     //修改密码
     Boolean doPwdModify(@Param("id") int id,@Param("newPassword") String newPassword);

     //删除用户
     Boolean delUserById(int id);
}
