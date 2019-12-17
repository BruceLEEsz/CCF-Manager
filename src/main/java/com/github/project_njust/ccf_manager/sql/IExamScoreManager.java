package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.ExamScore;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IExamScoreManager {

    /**
     * 插入新的分数结果
     * @param examscore 分数
     */
    void insertExamScore(@NotNull ExamScore examscore);

    /**
     * 更新一个已有的分数
     * @param examscore 分数
     */
    void updateExamScore(@NotNull ExamScore examscore);

    /**
     * 根据指定的UID和考试id查询
     * @param uid 用户id
     * @param examid 考试id
     * @return 分数
     */
    @Nullable
    ExamScore selectExamScore(int uid, int examid);

    /**
     * 获取某场考试的所有成绩
     * @param examid 考试id
     * @return 成绩列表
     */
    @NotNull
    List<@NotNull ExamScore> selectAllExamScore(int examid);

    /**
     * 获取某人所有考试的成绩
     * @param uid 用户id
     * @return 成绩列表
     */
    @NotNull
    List<@NotNull ExamScore> selectExamScoreByUser(int uid);

    /**
     * 根据指定条件查询.
     * <strong>主意, SQL语句必须以 "SELECT * FROM ExamScore" 开头</strong>
     * @param sql SQL语句
     * @param args 参数
     * @return 成绩列表
     */
    @NotNull
    List<@NotNull ExamScore> selectConditional(@NotNull String sql, @NotNull Object... args);
}
