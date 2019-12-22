package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.model.ExamScore;
import com.github.project_njust.ccf_manager.model.Student;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DeleteQualification extends Service {
    public DeleteQualification() {
        super("deleteQualification", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String studentid = input.getData().getString("studentID");
        Student student = SQLManager.getStudentManager().selectStudentByStudentID(studentid);
        if (student == null) {
            IResponse res = IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason", "找不到学生信息");
            return res;
        }

        ExamInfo examInfo = SQLManager.getExamInfoManager().getLastInfo();
        if (examInfo == null) {
            IResponse res = IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason", "找不到考试信息");
            return res;
        }
        int uid = student.getUid();
        int examid = examInfo.getExamid();

        @Nullable ExamScore examscore = SQLManager.getExamScoreManager().selectExamScore(uid, examid);
        examscore.setConfirm(false);
        SQLManager.getExamScoreManager().updateExamScore(examscore);

        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}
