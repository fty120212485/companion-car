package com.companioncar.backstage.service.impl;

import com.companioncar.backstage.service.ParamService;
import com.companioncar.dal.dao.ParamMapper;
import com.companioncar.dal.model.Param;
import com.companioncar.dal.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParamServiceImpl implements ParamService {

    @Autowired
    private ParamMapper paramMapper;

    @Override
    public Param detail(String paramId) {
        return paramMapper.selectByPrimaryKey(paramId);
    }

    @Override
    public int delete(String paramId) {
        return paramMapper.deleteByPrimaryKey(paramId);
    }

    @Override
    public int update(Param param) {
        return paramMapper.updateByPrimaryKey(param);
    }

    @Override
    public int insert(Param param) {
        param.setParamId(UUIDUtil.getUUID());
        return paramMapper.insert(param);
    }

    @Override
    public List<Param> list(Param param) {
        List<Param> list = paramMapper.list(param);
        if(list.size() > 0){
            return list;
        }
        return null;
    }
}
