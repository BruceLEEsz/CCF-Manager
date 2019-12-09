package com.github.project_njust.ccf_manager.dao;

import com.github.project_njust.ccf_manager.model.ID;

public interface IDMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(ID record);

    int insertSelective(ID record);

    ID selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(ID record);

    int updateByPrimaryKey(ID record);
}