package com.sust.spring.controller;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.sust.spring.domain.Bill;
import com.sust.spring.domain.Provider;
import com.sust.spring.service.BillService;
import com.sust.spring.service.ProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/bill")
public class BillController {

    @Resource
    private BillService billService;

    @Resource
    private ProviderService providerService;

    /**
     * 查询全部的订单信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/findAllBill")
    public String findAllBill(Model model){
        List<Bill> billList = billService.findAllBill();
        List<Provider> providerList = providerService.findAllProvider();
        model.addAttribute("billList",billList);
        model.addAttribute("providerList",providerList);
        return "billlist";
    }


    @RequestMapping("/getBillList")
    public String getBillList(Model model,@RequestParam(value = "queryProductName") String queryProductName ,
                                @RequestParam(value = "queryProviderId") int queryProviderId,
                              @RequestParam("queryIsPayment") int queryIsPayment){
        List<Bill> billList = billService.getBillList(queryProductName,queryProviderId,queryIsPayment);
        model.addAttribute("billList",billList);
        return "billlist";
    }

    /**
     * 根据id查询订单信息
     * @param model
     * @param billid
     * @return
     */
    @RequestMapping("/billview")
    public String getBillById(Model model, @RequestParam(value = "billid") int billid){
        Bill bill = billService.getBillById(billid);
        model.addAttribute("bill",bill);
        return "billview";
    }

    /**
     * 跳转到订单新增页面
     * @return
     */
    @RequestMapping(value="/billAdd",method = RequestMethod.GET)
    public String billAdd(){
        return "billadd";
    }


    /**
     * 发送Ajax请求获取供应商信息
     * @return
     */
    @RequestMapping(value = "/getProviderList",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object getProviderList(){
        List<Provider> providerList = providerService.findAllProvider();
        return JSONArray.toJSONString(providerList);
    }

    /**
     * 执行订单新增
     * @param bill
     * @return
     */
    @RequestMapping(value = "/doBillAdd",method = RequestMethod.POST)
    public String doBillAdd(Bill bill){

        System.out.println(bill.getProviderId());
        billService.doBillAdd(bill);
        return "redirect:/bill/findAllBill";
    }

    /**
     * 修改准备（跳转到订单修改页面）
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/billModify",method = RequestMethod.GET)
    public String billModify(Model model,@RequestParam(value = "billid") int id){
        Bill bill = billService.getBillById(id);
        model.addAttribute("bill",bill);
        return "billmodify";
    }

    /**
     * 修改订单信息
     * @param bill
     * @return
     */
    @RequestMapping(value = "/doBillModify",method = RequestMethod.POST)
    public String doBillModify(Bill bill){
        billService.doBillModify(bill);
        return "redirect:/bill/findAllBill";
    }

    @RequestMapping(value = "/delBillById",method = RequestMethod.GET)
    @ResponseBody
    public Object delBillById(@RequestParam(value = "billid") String id){

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(id)){
            resultMap.put("delResult", "notexist");
        }else{
            if(billService.delBillById(Integer.parseInt(id)))
                resultMap.put("delResult", "true");
            else
                resultMap.put("delResult", "false");
        }
        return JSONArray.toJSONString(resultMap);

    }
}
