package com.sust.spring.mapper;

import com.sust.spring.domain.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {
     //查询全部订单
     List<Bill> findAllBill();

     //根据商品名称、供应商、是否付款查询订单信息
     List<Bill> getBillList(@Param("productName") String productName,
                            @Param("providerId") int providerId,
                            @Param("isPayment") int isPayment);

     //根据id查询订单信息
     Bill getBillById(int id);

     //新增订单
     void doBillAdd(Bill bill);

     //修改订单
     void doBillModify(Bill bill);

     //删除订单
     Boolean delBillById(int id);

}
