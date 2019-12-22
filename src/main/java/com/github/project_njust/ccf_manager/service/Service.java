package com.github.project_njust.ccf_manager.service;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.service.kt.CoroutinesService;

import org.jetbrains.annotations.NotNull;


public abstract class Service {
    @NotNull
    protected final UserType @NotNull [] allowTypes;
    @NotNull
    protected final String name;

    /**
     * 构造服务
     *
     * @param name 服务名
     * @parm types 允许访问的用户类型
     */
    protected Service(@NotNull String name, @NotNull UserType @NotNull ... types) {
        this.name = name;
        this.allowTypes = types;
    }

    /**
     * @param input 用户提交的参数以及用户信息
     * @return 将返回用会的json
     */
    @NotNull
    public abstract IResponse onRequest(@NotNull ISubmitData input);

    @NotNull
    public UserType @NotNull [] getAllowTypes() {
        return allowTypes;
    }

    @NotNull
    public String getName() {
        return name;
    }

    /**
     * @return 是否支持协程
     */
    public final boolean isCoroutines() {
        return this instanceof CoroutinesService;
    }

}
