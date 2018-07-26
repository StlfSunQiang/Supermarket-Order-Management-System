package com.sust.spring.service;


import com.sust.spring.domain.Bill;

import java.util.List;

public interface BillService {
    //查询全部的订单信息
    List<Bill> findAllBill();

    //根据商品名称、供应商、是否付款查询订单信息
    List<Bill> getBillList(String productName,int providerId,int isPayment);

    //根据id查询订单信息
    Bill getBillById(int id);

    //新增订单信息
    void doBillAdd(Bill bill);

    //修改订单信息
    void doBillModify(Bill bill);

    //删除订单
    Boolean delBillById(int id);
}
