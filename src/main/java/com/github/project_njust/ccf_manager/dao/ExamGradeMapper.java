package com.github.project_njust.ccf_manager.dao;

import com.github.project_njust.ccf_manager.model.ExamGrade;

public interface ExamGradeMapper {
    int deleteByPrimaryKey(Integer studentid);

    int insert(ExamGrade record);

    int insertSelective(ExamGrade record);

    ExamGrade selectByPrimaryKey(Integer studentid);

    int updateByPrimaryKeySelective(ExamGrade record);

    int updateByPrimaryKey(ExamGrade record);
}