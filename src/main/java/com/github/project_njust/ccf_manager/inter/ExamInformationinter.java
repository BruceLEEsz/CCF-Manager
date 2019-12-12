package com.github.project_njust.ccf_manager.inter;

import com.github.project_njust.ccf_manager.model.Examinformation;
import com.github.project_njust.ccf_manager.model.User;

import java.util.List;

public interface ExamInformationinter {
    public int insertExamInformation(Examinformation record);

    public int updateExamInformation (Examinformation record,int ExamID);

    public int deleteExamInformation(int ExamId);

    public Examinformation selectExamInformationByExamId(int ExamId);

    public List<User> selectAllUser();

}
