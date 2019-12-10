package com.github.project_njust.ccf_manager.service;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

import org.jetbrains.annotations.NotNull;

public class ExampleService extends Service {

    public ExampleService(){
        super("example", UserType.TOURISTS);
    }

    @Override
    public @NotNull JsonSection onRequest(@NotNull ISubmitData input) {
        JsonSection result = JsonSection.createSection();
        String from = input.getData().getString("fileId");
        System.out.println(from);
        result.set("输出结果键", from);
        return result;
    }
}
