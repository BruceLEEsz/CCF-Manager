package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.ExamInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IExamInfoManager {

    int insertExamInformation(@NotNull ExamInfo record);

    int updateExamInformation(@NotNull ExamInfo record, int ExamID);

    int deleteExamInformation(int ExamId);

    @Nullable
    ExamInfo selectExamInformationByExamId(int ExamId);

}
