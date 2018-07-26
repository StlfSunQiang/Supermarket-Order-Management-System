package com.sust.spring.controller;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.sust.spring.domain.Provider;
import com.sust.spring.service.ProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderController {

    @Resource
    private ProviderService providerService;

    /**
     * 查询全部的供应商信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/findAllProvider")
    public String findProvider(Model model){
        List<Provider> providerList = providerService.findAllProvider();
        model.addAttribute("providerList",providerList);
        return "providerlist";
    }

    /**
     * 根据供应商名称、编码查询供应商信息（模糊查询）
     * @param model
     * @param queryProName
     * @param queryProCode
     * @return
     */
    @RequestMapping(value = "/providerList")
    public String getProviderList(Model model, @RequestParam(value = "queryProName") String queryProName,
                                  @RequestParam(value = "queryProCode") String queryProCode){
        System.out.println(queryProName+queryProCode);
        List<Provider> providerList = providerService.getProviderList(queryProName,queryProCode);
        System.out.println(providerList);
        model.addAttribute("providerList",providerList);
        model.addAttribute("queryProName",queryProName);
        model.addAttribute("queryProCode",queryProCode);

        return "providerlist";
    }

    /**
     * 根据id查询供应商信息
     * @param model
     * @param proid
     * @return
     */
    @RequestMapping(value = "/getProviderById")
    public String getProvideById(Model model,@RequestParam(value = "proid") int proid){
        Provider provider = providerService.getProviderById(proid);
        model.addAttribute("provider",provider);
        return "providerview";
    }


    /**
     * 跳转到供应商信息新增页面
     * @return
     */
    @RequestMapping(value = "/providerAdd",method = RequestMethod.GET)
    public String providerAdd(){
        return "provideradd";
    }

    /**
     * 新增供应商信息
     * @param provider
     * @return
     */
    @RequestMapping(value = "/doProviderAdd",method = RequestMethod.POST)
    public String doProviderAdd(Provider provider){
        providerService.doProviderAdd(provider);
        return "redirect:/provider/findAllProvider";
    }


    /**
     * 修改准备（跳转到修改页面）
     */
    @RequestMapping(value = "/providerModify")
    public String providerModify(Model model,@RequestParam(value = "proid") int id){
        Provider provider = providerService.getProviderById(id);
        model.addAttribute("provider",provider);
        return "providermodify";
    }

    /**
     * 修改供应商信息
     * @param provider
     * @return
     */
    @RequestMapping(value = "doProviderModofy",method = RequestMethod.POST)
    public String doProviderModify(Provider provider){
        System.out.println(provider.getProCode());
        providerService.doProviderModify(provider);
        return "redirect:/provider/findAllProvider";
    }

    /**
     * 删除供应商信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/delProviderById",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object delProviderById(@RequestParam(value = "proid") String id){
        HashMap<String,String> resultMap = new HashMap<String,String>();
        if (StringUtils.isNullOrEmpty(id)){
            resultMap.put("delResult","notexist");
        }else{
            if (providerService.delProviderById(Integer.parseInt(id))){
                resultMap.put("delResult","true");
            }else{
                resultMap.put("delResult","false");
            }
        }

        return JSONArray.toJSONString(resultMap);
    }
}
