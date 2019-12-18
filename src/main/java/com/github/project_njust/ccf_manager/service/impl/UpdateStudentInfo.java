package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import jdk.internal.util.xml.impl.Input;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class UpdateStudentInfo extends Service {
    public UpdateStudentInfo(){
        super("updateStudentInfo", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String uuid = input.getData().getString("uuid");
        UUID id = UUID.fromString(uuid);


        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}


