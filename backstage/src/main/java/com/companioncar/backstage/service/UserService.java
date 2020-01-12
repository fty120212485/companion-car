package com.companioncar.backstage.service;

import com.companioncar.backstage.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    User selectBy(String userId);

    List<User> list(User record);

    int update(User record);

    int insert(User record);

    int delete(String userId);

    String findByRoleCode(String userId);

    User findByUserName(String username);

    int addRole(String roleId, String userId);

    boolean isHasRole(String roleId, String userId);
}
