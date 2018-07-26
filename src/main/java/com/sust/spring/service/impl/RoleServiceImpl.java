package com.sust.spring.service.impl;


import com.sust.spring.domain.Role;
import com.sust.spring.mapper.RoleMapper;
import com.sust.spring.service.RoleService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAllRole() {
        return roleMapper.findAllRole();
    }
}
