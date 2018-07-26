package com.sust.spring.controller;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.sust.spring.domain.Role;
import com.sust.spring.domain.User;
import com.sust.spring.service.RoleService;
import com.sust.spring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/dologin",method = RequestMethod.POST)
    public String doLogin(@RequestParam String userCode,@RequestParam String userPassword, HttpServletRequest request, HttpSession session){
         User user = userService.checkLogin(userCode,userPassword);
         if (user != null){
             session.setAttribute("userSession",user);
             return "redirect:/user/main";
         }else{
             request.setAttribute("error","用户名或密码不正确");
             return "error";
         }
    }
    @RequestMapping(value = "/main")
    public String main(HttpSession session){
        if (session.getAttribute("userSession") == null){
            return "redirect:/user/dologin";
        }else{
            return "frame";
        }
    }

    /**
     * 注销
     * @param session
     * @return
     */
    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logout(HttpSession session){
        //清除session
        session.removeAttribute("userSession");
        return "login";
    }
//    @RequestMapping(value="/exlogin",method=RequestMethod.GET)
//    public String exLogin(@RequestParam String userCode,@RequestParam String userPassword){
//        //调用service方法，进行用户匹配
//        User user = userService.checkLogin(userCode,userPassword);
//        if(null == user){//登录失败
//            throw new RuntimeException("用户名或者密码不正确！");
//        }
//        return "redirect:/user/main";
//    }

    /**
     *跳转到修改密码页面，如果session过期则返回到登录页面
     * @return
     */
    @RequestMapping(value = "/pwdmodify",method = RequestMethod.GET)
    public String pwdModify(HttpSession session){
        if (session.getAttribute("userSession") == null){
            return "redirect:/user/login.html";
        }
        return "pwdmodify";
    }

    /**
     * Ajax请求 验证原密码是否正确（修改密码）
     * @param oldpassword
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/oldPwdValid",method = RequestMethod.GET)
    public Object oldPwdValid(@RequestParam String oldpassword,HttpSession session) throws Exception{

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (null == session.getAttribute("userSession")){//session过期
            resultMap.put("result","sessionerror");
        }else if (StringUtils.isNullOrEmpty(oldpassword)){//旧密码输入为空
            resultMap.put("result","error");
        }else{
            String sessionPwd = ((User)session.getAttribute("userSession")).getUserPassword();
            if (oldpassword.equals(sessionPwd)) {
                resultMap.put("result","true");
            }else{//旧密码输入不正确
                resultMap.put("result","false");
            }
        }
        return JSONArray.toJSONString(resultMap);
    }

    /**
     * 修改密码
     * @param newPassword
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/doPwdModify",method = RequestMethod.POST)
    public String doPwdModify(@RequestParam(value = "newpassword") String newPassword,HttpSession session,HttpServletRequest request){
        boolean flag = false;
        Object o = session.getAttribute("userSession");
        if (o != null && !StringUtils.isNullOrEmpty(newPassword)){
            flag = userService.doPwdModify(((User) o).getId(),newPassword);
            if (flag){
                request.setAttribute("success","密码修改成功");
                session.removeAttribute("userSession");
                return "redirect:/user/login";
            }else{
                request.setAttribute("fail","密码修改失败");
            }
        }else{
            request.setAttribute("fail","密码修改失败");
        }
        return "pwdmodify";
    }

    /**
     * 查询全部用户
     * @param model
     * @return
     */
    @RequestMapping(value = "/findAllUser")
    public String findAllUser(Model model){
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList",userList);
        return "userlist";
    }

    /**
     * 根据用户名和用户角色获取用户信息
     * @param model
     * @param queryname
     * @param userRole
     * @return
     */
    @RequestMapping(value = "/getUserList")
    public String getUserList(Model model, @RequestParam(value = "queryname") String queryname,
                              @RequestParam(value = "userRole") String userRole){
        System.out.println(queryname+userRole);
        List<User> userList = userService.getUserList(queryname,userRole);
        System.out.println(userList);
        model.addAttribute("userList",userList);
        return "userlist";
    }

    /**
     * 根据id查询用户信息
     * @param model
     * @param uid
     * @return
     */
    @RequestMapping(value = "/userview")
    public String getUserById(Model model,@RequestParam(value = "uid") int uid){
        User user = userService.getUserById(uid);
        model.addAttribute("user",user);
        return "userview";
    }


    /**
     * 跳转到新增页面
     * @return
     */
    @RequestMapping(value = "/userAdd",method = RequestMethod.GET)
    public String userAdd(){
        return "useradd";
    }

    /**
     * 发送ajax请求获取（角色信息）用户角色
     * @return
     */
    @RequestMapping(value = "/findAllRole",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object findAllRole(){

        List<Role> roleList = roleService.findAllRole();
        return JSONArray.toJSONString(roleList);
    }

    /**
     * 新增用户时判断要新增的用户是否已经存在
     * @param userCode
     * @return
     */
    @RequestMapping(value = "/ucexist", method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object ucexist(@RequestParam(value = "userCode") String userCode){

        HashMap<String, String> result=new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(userCode)){
            result.put("userCode", "exist");
        }else{
            try {
                if(userService.ucexist(userCode)!=null){
                    result.put("userCode", "exist");
                }else{
                    result.put("userCode", "noexist");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return JSONArray.toJSONString(result);
    }

    /**
     * 新增用户信息
     * @return
     */
    @RequestMapping(value = "/doUserAdd",method = RequestMethod.POST)
    public String addUser(User user){

        userService.addUser(user);
        return "redirect:/user/findAllUser";
    }

    /**
     * 跳转到修改用户页面
     * @return
     */
    @RequestMapping(value = "/userModify",method = RequestMethod.GET)
    public String userModify(Model model,@RequestParam(value = "uid") int id){
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        return "usermodify";
    }

    /**
     * 修改用户信息
     * @return
     */
    @RequestMapping(value ="/doUserModify",method = RequestMethod.POST)
    public String doUserModify(User user,@RequestParam(value = "uid") int id){
        user.setId(id);
        userService.doUserModify(user);
        return "redirect:/user/findAllUser";

    }

    @RequestMapping(value="/delUserById",method=RequestMethod.GET)
    @ResponseBody
    public Object delUserById(@RequestParam(value = "userid") String id){

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(id)){
            resultMap.put("delResult", "notexist");
        }else{
            if(userService.delUserById(Integer.parseInt(id)))
                resultMap.put("delResult", "true");
            else
                resultMap.put("delResult", "false");
        }
        return JSONArray.toJSONString(resultMap);
    }


}
