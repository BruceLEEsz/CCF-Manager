package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.Examscore;

import java.util.List;

public interface IExamScoreManager {
    int insertExamScore(Examscore examscore);

    int updateExamScore(Examscore examscore, int uid);

    int deleteExamScore(int uid);

    Examscore selectExamScoreById(int uid);

    List<Examscore> selectAllExamScore(int examid);


}
