package com.companioncar.backstage.service.impl;

import com.companioncar.backstage.service.UserService;
import com.companioncar.backstage.dao.UserMapper;
import com.companioncar.backstage.model.User;
import com.companioncar.dal.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectBy(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> list(User record) {
        List<User> list = userMapper.list(record);
        if(list.size() > 0){
            return list;
        }
        return null;
    }

    @Override
    public int update(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insert(User record) {
        record.setUserId(UUIDUtil.getUUID());
        return userMapper.insert(record);
    }

    @Override
    public int delete(String userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public String findByRoleCode(String userId) {
        return userMapper.findByRoleCode(userId);
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public int addRole(String roleId, String userId) {
        return userMapper.addRole(roleId, userId);
    }

    @Override
    public boolean isHasRole(String roleId, String userId) {
        int count = userMapper.isHasRole(roleId, userId);
        if(count > 0){
            return true;
        }
        return false;
    }
}

