package com.sust.spring.service.impl;

import com.sust.spring.domain.Provider;
import com.sust.spring.mapper.ProviderMapper;
import com.sust.spring.service.ProviderService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("providerService")
public class ProviderServiceImpl implements ProviderService {

    @Resource
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> findAllProvider() {
        return providerMapper.findAllProvider();
    }

    @Override
    public List<Provider> getProviderList(String proName, String proCode) {
        return providerMapper.getProviderList(proName,proCode);
    }

    @Override
    public Provider getProviderById(int id) {
        return providerMapper.getProviderById(id);
    }

    @Override
    public void doProviderAdd(Provider provider) {
        providerMapper.doProviderAdd(provider);
    }

    @Override
    public void doProviderModify(Provider provider) {
        providerMapper.doProviderModify(provider);
    }

    @Override
    public Boolean delProviderById(int id) {
        return providerMapper.delProviderById(id);
    }
}
