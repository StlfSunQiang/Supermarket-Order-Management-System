package com.sust.spring.mapper;

import com.sust.spring.domain.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {

    //查询所有的供应商信息
    List<Provider> findAllProvider();

    //根据供应商名称、编码查询供应商信息（模糊查询）
    List<Provider> getProviderList(@Param("proName") String proName,@Param("proCode") String proCode);

    //根据id查询供应商信息
    Provider getProviderById(int id);

    //新增供应商信息
    void doProviderAdd(Provider provider);

    //修改供应商信息
    void doProviderModify(Provider provider);

    //删除供应商信息
    Boolean delProviderById(int id);

}
