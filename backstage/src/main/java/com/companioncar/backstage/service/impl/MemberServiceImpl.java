package com.companioncar.backstage.service.impl;

import com.companioncar.backstage.service.MemberService;
import com.companioncar.dal.dao.MemberMapper;
import com.companioncar.dal.model.Member;
import com.companioncar.dal.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member selectBy(String userId) {
        return memberMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<Member> list(Member record) {
        List<Member> list = memberMapper.list(record);
        if(list.size() > 0){
            return list;
        }
        return null;
    }

    @Override
    public int update(Member record) {
        return memberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insert(Member record) {
        record.setMemberId(UUIDUtil.getUUID());
        return memberMapper.insert(record);
    }

    @Override
    public int delete(String userId) {
        return memberMapper.deleteByPrimaryKey(userId);
    }
}

