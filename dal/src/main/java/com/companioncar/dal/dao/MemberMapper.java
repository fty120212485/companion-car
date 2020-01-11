package com.companioncar.dal.dao;

import com.companioncar.dal.model.Member;

import java.util.List;

public interface MemberMapper {
    int deleteByPrimaryKey(String memberId);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(String memberId);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    List<Member> list(Member record);
}