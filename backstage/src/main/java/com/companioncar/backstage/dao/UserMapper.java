package com.companioncar.backstage.dao;

import com.companioncar.backstage.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> list(User record);

    String findByRoleCode(String userId);

    User findByUserName(String username);

    int addRole(@Param("roleId")String roleId, @Param("userId")String userId);

    int isHasRole(@Param("roleId")String roleId, @Param("userId")String userId);

    int userInit(User record);
}