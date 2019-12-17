package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.ExamInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;

public interface IExamInfoManager {

    /**
     * 创建新的考试信息
     * @param score 可以免费的分数线
     * @param date 报名截止日期
     * @return 创建的新的信息
     */
    @NotNull ExamInfo createExamInformation(int score, Date date);

    /**
     * 更新一个考试信息
     * @param record 要更新的考试信息
     */
    void updateExamInformation(@NotNull ExamInfo record);

    /**
     * 根据考试id寻找
     * @param ExamId 考试id
     * @return 考试信息
     */
    @Nullable
    ExamInfo selectExamInformationByExamId(int ExamId);

}
