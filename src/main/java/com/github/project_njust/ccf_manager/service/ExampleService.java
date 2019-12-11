package com.github.project_njust.ccf_manager.service;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

import org.jetbrains.annotations.NotNull;

public class ExampleService extends Service {

    public ExampleService() {
        super("example", UserType.TOURISTS);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        IResponse result = IResponse.createIResponse();
        String from = input.getData().getString("fileId");
        System.out.println(from);
        result.set("输出结果键", from);
        return result;
    }
}
