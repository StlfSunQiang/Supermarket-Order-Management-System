package com.sust.spring.controller;

import com.alibaba.fastjson.JSONArray;
import com.sust.spring.domain.Role;
import com.sust.spring.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 发送ajax请求获取（角色信息）用户角色
     * userlist.js
     * @return
     */
    @RequestMapping(value = "/rolelist",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object findAllRole(){

        List<Role> roleList = roleService.findAllRole();
        return JSONArray.toJSONString(roleList);
    }
}
