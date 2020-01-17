package com.companioncar.dal.dao;

import com.companioncar.dal.model.Tag;

import java.util.List;

public interface TagMapper {
    int deleteByPrimaryKey(String tagId);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(String tagId);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    List<Tag> list(Tag tag);
}