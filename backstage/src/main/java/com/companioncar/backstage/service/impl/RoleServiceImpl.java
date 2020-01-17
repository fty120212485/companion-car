package com.companioncar.backstage.service.impl;

import com.companioncar.backstage.dao.UserMapper;
import com.companioncar.backstage.model.Authority;
import com.companioncar.backstage.model.AuthorityAndRole;
import com.companioncar.backstage.model.User;
import com.companioncar.backstage.service.RoleService;
import com.companioncar.backstage.dao.RoleMapper;
import com.companioncar.backstage.model.Role;
import com.companioncar.dal.util.MD5Util;
import com.companioncar.dal.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Role selectBy(String roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public List<Role> list(Role record) {
        List<Role> list = roleMapper.list(record);
        if(list.size() > 0){
            return list;
        }
        return null;
    }

    @Override
    public int update(Role record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insert(Role record) {
        record.setRoleId(UUIDUtil.getUUID());
        return roleMapper.insert(record);
    }

    @Override
    public int delete(String userId) {
        return roleMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int setStatus(Role record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public boolean isHasAuthority(AuthorityAndRole record) {
        int result = roleMapper.isHasAuthority(record);
        if(result > 0){
            return true;
        }
        return false;
    }

    @Override
    public int addAuthority(AuthorityAndRole record) {
        record.setAuthorityRoleId(UUIDUtil.getUUID());
        return roleMapper.addAuthority(record);
    }

    @Override
    public Role findByRoleCode(String roleName) {
        return roleMapper.findByRoleCode(roleName);
    }

    @Override
    public Role roleInit(){
        Role role = new Role();
        role.setRoleId(UUIDUtil.getUUID());
        role.setRoleName("管理员");
        role.setRoleCode("admin");
        byte isDelete = 1;
        role.setIsDelete(isDelete);
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        roleMapper.roleInit(role);
        User user = userMapper.findByUserName("admin");
        if(user == null){
            user = new User();
            user.setUserId(UUIDUtil.getUUID());
            user.setUsername("admin");
            String salt = MD5Util.getSalt();
            user.setPassword(MD5Util.getMd5("123456", salt));
            user.setSalt(salt);
            byte is_super = 1;
            user.setIsSuper(is_super);
            userMapper.userInit(user);
            userMapper.addRole(role.getRoleId(),user.getUserId());
        }
        return role;
    }

    @Override
    public int insertBatch(List<Authority> record, String roleId) {
        if(record.size() > 0){
            List<AuthorityAndRole> list = new ArrayList<>();
            for (Authority auth : record) {
                AuthorityAndRole authAndRole = new AuthorityAndRole();
                authAndRole.setAuthorityId(auth.getAuthorityId());
                authAndRole.setRoleId(roleId);
                list.add(authAndRole);
            }
            return roleMapper.insertBatch(list);
        }
        return 0;
    }
}

