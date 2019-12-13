package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.ExamInfo;

public interface IExamInfoManager {

    int insertExamInformation(ExamInfo record);

    int updateExamInformation(ExamInfo record, int ExamID);

    int deleteExamInformation(int ExamId);

    ExamInfo selectExamInformationByExamId(int ExamId);

}
