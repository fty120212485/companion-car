package com.companioncar.backstage.service.impl;

import com.companioncar.backstage.model.AuthorityAndRole;
import com.companioncar.backstage.service.AuthorityService;
import com.companioncar.backstage.dao.AuthorityMapper;
import com.companioncar.backstage.model.Authority;
import com.companioncar.dal.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public Authority selectBy(String authorityId) {
        return authorityMapper.selectByPrimaryKey(authorityId);
    }

    @Override
    public List<Authority> list(Authority record) {
        List<Authority> list = authorityMapper.list(record);
        if(list.size() > 0){
            return list;
        }
        return null;
    }

    @Override
    public int update(Authority record) {
        return authorityMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insert(Authority record) {
        String authorityId = UUIDUtil.getUUID();
        record.setAuthorityId(authorityId);
        return authorityMapper.insert(record);
    }

    @Override
    public int delete(String authorityId) {
        return authorityMapper.deleteByPrimaryKey(authorityId);
    }

    @Override
    public List<AuthorityAndRole> findAuthorityAndRole() {
        List<AuthorityAndRole> list = authorityMapper.findAuthorityAndRole();
        if(list.size() > 0){
            return list;
        }
        return null;
    }

    @Override
    public int insertBatch(List<Authority> list) {
        if(list.size() > 0){
            authorityMapper.insertBatch(list);
            return 1;
        }
        return 0;
    }

    @Override
    public List<String> findUrlAll() {
        List<String> list = authorityMapper.findUrlAll();
        if(list.size() > 0){
            return list;
        }
        return null;
    }
}

