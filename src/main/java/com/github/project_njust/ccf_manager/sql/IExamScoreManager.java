package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.Examscore;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IExamScoreManager {
    int insertExamScore(@NotNull Examscore examscore);

    int updateExamScore(@NotNull Examscore examscore, int uid);

    int deleteExamScore(int uid);

    @Nullable
    Examscore selectExamScoreById(int uid);

    @NotNull
    List<@NotNull Examscore> selectAllExamScore(int examid);


}
