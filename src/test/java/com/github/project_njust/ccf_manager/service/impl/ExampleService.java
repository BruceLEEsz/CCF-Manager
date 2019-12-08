package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.service.Service;
import com.github.project_njust.ccf_manager.service.SubmitData;
import com.github.project_njust.ccf_manager.servlet.FileUploadServlet;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

public class ExampleService extends Service {

    public ExampleService(){
        super("example", UserType.TOURISTS);
    }

    @Override
    public @NotNull JsonSection onRequest(@NotNull SubmitData input) {
        JsonSection result = JsonSection.createSection();
        String from = input.getData().getString("fileId");
        UUID uid = UUID.fromString(from);
        File f = FileUploadServlet.Companion.getCacheFiles().get(uid);
        //TODO()
        result.set("输出结果键", from);
        return result;
    }
}
