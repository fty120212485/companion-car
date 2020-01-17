package com.companioncar.backstage.service;

import com.companioncar.dal.model.Brand;

import java.util.List;

public interface BrandService {

    int delete(String brandId);

    int insert(Brand record);

    Brand detail(String brandId);

    int update(Brand record);

    List<Brand> list(Brand record);
}
