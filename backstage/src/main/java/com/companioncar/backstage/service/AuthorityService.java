package com.companioncar.backstage.service;

import com.companioncar.backstage.model.Authority;
import com.companioncar.backstage.model.AuthorityAndRole;

import java.util.List;

public interface AuthorityService {
    Authority selectBy(String authorityId);

    List<Authority> list(Authority record);

    int update(Authority record);

    int insert(Authority record);

    int delete(String userId);

    List<AuthorityAndRole> findAuthorityAndRole();

    int insertBatch(List<Authority> list);

    List<String> findUrlAll();
}
