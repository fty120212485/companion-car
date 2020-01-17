package com.companioncar.backstage.service;

import com.companioncar.dal.model.Favorite;
import com.companioncar.dal.model.Member;
import com.companioncar.dal.model.MemberCar;
import com.companioncar.dal.model.Point;

import java.util.List;

public interface MemberService {
    Member selectBy(String memberId);

    List<Member> list(Member record);

    int update(Member record);

    int insert(Member record);

    int delete(String userId);

    List<Favorite> favorite(String memberId);

    List<MemberCar> memberCar(String memberId, byte type);

    List<Point> memberPoint(String memberId);
}
