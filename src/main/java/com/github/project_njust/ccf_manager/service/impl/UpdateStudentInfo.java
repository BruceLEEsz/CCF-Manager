package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.excel.ExcelUtil;
import com.github.project_njust.ccf_manager.model.Student;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import jdk.internal.util.xml.impl.Input;
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
            for (Student student : students) {
                int uid = student.getUid();
                @Nullable Student student1 = SQLManager.getStudentManager().selectStudent(uid);
                if(student1==null){
                    SQLManager.getStudentManager().insertStudent(student);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}


