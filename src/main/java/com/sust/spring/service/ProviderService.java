package com.sust.spring.service;

import com.sust.spring.domain.Provider;

import java.util.List;

public interface ProviderService {
    List<Provider> findAllProvider();
    List<Provider> getProviderList(String proName,String proCode);
    Provider getProviderById(int id);
    void doProviderAdd(Provider provider);
    void doProviderModify(Provider provider);
    Boolean delProviderById(int id);
}
