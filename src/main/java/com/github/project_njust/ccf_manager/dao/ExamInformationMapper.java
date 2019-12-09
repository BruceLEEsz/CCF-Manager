package com.github.project_njust.ccf_manager.dao;

import com.github.project_njust.ccf_manager.model.ExamInformation;

public interface ExamInformationMapper {
    int insert(ExamInformation record);

    int insertSelective(ExamInformation record);
}