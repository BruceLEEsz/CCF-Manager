package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.service.Service;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

import org.jetbrains.annotations.NotNull;

public class ExampleService extends Service {

    public ExampleService(){
        super("example", UserType.TOURISTS);
    }

    @Override
    public @NotNull JsonSection onRequest(@NotNull JsonSection input) {
        JsonSection result = JsonSection.createSection();
        String from = input.getString("传入数据键");
        result.set("输出结果键", from);
        return result;
    }
}
