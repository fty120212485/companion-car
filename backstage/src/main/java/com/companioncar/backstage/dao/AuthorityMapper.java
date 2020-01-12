package com.companioncar.backstage.dao;

import com.companioncar.backstage.model.Authority;
import com.companioncar.backstage.model.AuthorityAndRole;

import java.util.List;

public interface AuthorityMapper {
    int deleteByPrimaryKey(String authorityId);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(String authorityId);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);

    List<Authority> list(Authority record);

    List<AuthorityAndRole> findAuthorityAndRole();

    int insertBatch(List<Authority> list);

    List<String> findUrlAll();
}