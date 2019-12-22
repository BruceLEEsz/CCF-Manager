package com.github.project_njust.ccf_manager.service;

import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ISubmitData {

    /**
     * 获取用户提交的json信息
     *
     * @return 用户提交的json信息
     */
    @NotNull JsonSection getData();

    /**
     * 获取调用本次服务的用户,可能为空
     *
     * @return 调用本次服务的用户
     */
    @Nullable User getUser();
}
