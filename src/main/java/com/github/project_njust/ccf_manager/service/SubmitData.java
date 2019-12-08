package com.github.project_njust.ccf_manager.service;

import com.github.project_njust.ccf_manager.User;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SubmitData {

    @NotNull JsonSection getData();

    @Nullable User getUser();
}
