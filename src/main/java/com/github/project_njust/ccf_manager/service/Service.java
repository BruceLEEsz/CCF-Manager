package com.github.project_njust.ccf_manager.service;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

import org.jetbrains.annotations.NotNull;


public abstract class Service {
    @NotNull
    protected final UserType @NotNull [] allowTypes;
    @NotNull
    protected final String name;

    protected Service(@NotNull String name, @NotNull UserType @NotNull ... types) {
        this.name = name;
        this.allowTypes = types;
    }

    @NotNull
    public abstract JsonSection onRequest(@NotNull ISubmitData input);

    @NotNull

    public UserType @NotNull [] getAllowTypes() {
        return allowTypes;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public final boolean isCoroutines() {
        return this instanceof CoroutinesService;
    }

}
