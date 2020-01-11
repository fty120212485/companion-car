package com.companioncar.backstage.dao;

import com.companioncar.backstage.model.AuthorityAndRole;
import com.companioncar.backstage.model.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> list(Role record);

    int addAuthority(AuthorityAndRole record);

    Role findByRoleName(String roleName);

    int roleInit(Role record);

    int insertBatch(List<AuthorityAndRole> list);
}