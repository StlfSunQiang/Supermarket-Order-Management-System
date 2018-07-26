package com.sust.spring.service.impl;

import com.sust.spring.domain.Bill;
import com.sust.spring.mapper.BillMapper;
import com.sust.spring.service.BillService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("billService")
public class BillServiceImpl implements BillService {

    @Resource
    private BillMapper billMapper;

    @Override
    public List<Bill> findAllBill() {
        return billMapper.findAllBill();
    }

    @Override
    public List<Bill> getBillList(String productName, int providerId, int isPayment) {
        return billMapper.getBillList(productName,providerId,isPayment);
    }

    @Override
    public Bill getBillById(int id) {
        return billMapper.getBillById(id);
    }

    @Override
    public void doBillAdd(Bill bill) {
        billMapper.doBillAdd(bill);
    }

    @Override
    public void doBillModify(Bill bill) {
        billMapper.doBillModify(bill);
    }

    @Override
    public Boolean delBillById(int id) {
        return billMapper.delBillById(id);
    }
}
