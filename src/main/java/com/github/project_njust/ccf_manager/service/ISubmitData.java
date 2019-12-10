package com.github.project_njust.ccf_manager.service;

import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ISubmitData {

    @NotNull JsonSection getData();

    @Nullable User getUser();
}
