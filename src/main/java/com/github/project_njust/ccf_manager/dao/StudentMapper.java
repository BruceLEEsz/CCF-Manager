package com.github.project_njust.ccf_manager.dao;

import com.github.project_njust.ccf_manager.model.Student;

public interface StudentMapper {
    int insert(Student record);

    int insertSelective(Student record);
}