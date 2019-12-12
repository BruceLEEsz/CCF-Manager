package com.github.project_njust.ccf_manager.inter;

        import com.github.project_njust.ccf_manager.model.Examscore;

        import java.util.List;

public interface ExamScoreinter {
    public int insertExamScore(Examscore examscore);

    public int updateExamScore (Examscore examscore,int uid);

    public int deleteExamScore(int uid);

    public Examscore selectExamScoreById(int uid);

    public List<Examscore> selectAllExamScore();
}
