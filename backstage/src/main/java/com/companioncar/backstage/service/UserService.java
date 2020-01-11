package com.companioncar.backstage.service;

import com.companioncar.backstage.model.User;

import java.util.List;

public interface UserService {

    User selectBy(String userId);

    List<User> list(User record);

    int update(User record);

    int insert(User record);

    int delete(String userId);

    String findByRoleName(String userId);
}
