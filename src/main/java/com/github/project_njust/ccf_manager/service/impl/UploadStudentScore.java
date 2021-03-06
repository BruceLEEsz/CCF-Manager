package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.excel.ExcelUtil;
import com.github.project_njust.ccf_manager.model.ExamScore;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class UploadStudentScore extends Service {
    public UploadStudentScore() {
        super("uploadStudentScore", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String uuid = input.getData().getString("UUID");
        UUID id = UUID.fromString(uuid);

        try {
            List<ExamScore> examscores = ExcelUtil.loadScore(id);
            for (ExamScore examScore : examscores) {
                int uid = examScore.getUid();
                int examid = examScore.getExamid();
                @Nullable ExamScore examScore1 = SQLManager.getExamScoreManager().selectExamScore(uid, examid);
                if (examScore1 == null) {
                    SQLManager.getExamScoreManager().insertExamScore(examScore);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            IResponse res = IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason", "文件解析失败: " + throwable.getMessage());
            return res;
        }

        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}
