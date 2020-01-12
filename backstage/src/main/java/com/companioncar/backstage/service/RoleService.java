package com.companioncar.backstage.service;

import com.companioncar.backstage.model.Authority;
import com.companioncar.backstage.model.AuthorityAndRole;
import com.companioncar.backstage.model.Role;

import java.util.List;

public interface RoleService {
    Role selectBy(String memberId);

    List<Role> list(Role record);

    int update(Role record);

    int insert(Role record);

    int delete(String userId);

    int setStatus(Role record);

    boolean isHasAuthority(AuthorityAndRole record);

    int addAuthority(AuthorityAndRole record);

    Role findByRoleCode(String roleCode);

    Role roleInit();

    int insertBatch(List<Authority> record, String roleId);
}
