package com.companioncar.backstage.service;

import com.companioncar.dal.model.Member;

import java.util.List;

public interface MemberService {
    Member selectBy(String memberId);

    List<Member> list(Member record);

    int update(Member record);

    int insert(Member record);

    int delete(String userId);
}
