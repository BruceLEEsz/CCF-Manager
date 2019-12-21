package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.model.ExamScore;
import com.github.project_njust.ccf_manager.model.Student;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GetApplyList extends Service {
    public GetApplyList(){
        super("getApplyList", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        ExamInfo examInfo=SQLManager.getExamInfoManager().getLastInfo();
        if(examInfo==null) {
            IResponse response=IResponse.createIResponse(IResponse.Status.ERROR);
            response.set("reason","数据库异常");
            return  response;
        }
        List<ExamScore> examScores=SQLManager.getExamScoreManager().selectAllExamScore(examInfo.getExamid());
        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        List<JsonSection> result =new ArrayList<>();
        for(ExamScore es : examScores){
            JsonSection js = JsonSection.createSection();
            Student student = SQLManager.getStudentManager().selectStudent(es.getUid());
            js.set("name",student.getData().getString("Name"));
            js.set("studentID", student.getStudentId());
            js.set("confirm", es.getConfirm());
            result.add(js);
            System.out.println("examScores: "+js);
        }
        res.set("students", result);
        return res;
    }
}

