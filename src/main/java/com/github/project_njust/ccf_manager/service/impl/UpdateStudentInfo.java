package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.excel.ExcelUtil;
import com.github.project_njust.ccf_manager.model.Student;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpdateStudentInfo extends Service {
    public UpdateStudentInfo(){
        super("updateStudentInfo", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String uuid = input.getData().getString("uuid");
        UUID id = UUID.fromString(uuid);

        try {
            List<Student> students = ExcelUtil.loadStudents(id);
            for (Student students1 : students) {
                int uid = students1.getUid();
                @Nullable Student student = SQLManager.getStudentManager().selectStudent(uid);
                if(student==null){
                    SQLManager.getStudentManager().insertStudent(students1);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}


