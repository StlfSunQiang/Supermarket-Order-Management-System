package com.sust.spring.mapper;

import com.sust.spring.domain.Role;

import java.util.List;

public interface RoleMapper {

    //查询全部的角色信息
    List<Role> findAllRole();
}
