package com.companioncar.backstage.service.impl;

import com.companioncar.backstage.service.MemberService;
import com.companioncar.dal.dao.FavoriteMapper;
import com.companioncar.dal.dao.MemberCarMapper;
import com.companioncar.dal.dao.MemberMapper;
import com.companioncar.dal.dao.PointMapper;
import com.companioncar.dal.model.Favorite;
import com.companioncar.dal.model.Member;
import com.companioncar.dal.model.MemberCar;
import com.companioncar.dal.model.Point;
import com.companioncar.dal.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private MemberCarMapper memberCarMapper;

    @Autowired
    private PointMapper pointMapper;

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

    @Override
    public List<Favorite> favorite(String memberId) {
        List<Favorite> list = favoriteMapper.list(memberId);
        if(list.size() > 0){
            return list;
        }
        return null;
    }

    @Override
    public List<MemberCar> memberCar(String memberId, byte type) {
        List<MemberCar> list = memberCarMapper.memberCar(memberId, type);
        if(list.size() >0){
            return list;
        }
        return null;
    }

    @Override
    public List<Point> memberPoint(String memberId) {
        List<Point> list = pointMapper.memberCar(memberId);
        if(list.size() >0){
            return list;
        }
        return null;
    }
}

