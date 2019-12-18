package com.github.project_njust.ccf_manager.wrapper.json;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public interface JsonSection {
    @NotNull
    String getName();


    @NotNull
    Set<@NotNull String> getKeys();

    boolean getBoolean(@NotNull String path);

    int getInt(@NotNull String path);

    long getLong(@NotNull String path);

    double getDouble(@NotNull String path);

    @Nullable
    String getString(@NotNull String path);

    @Nullable
    List<@Nullable Object> getList(@NotNull String path);

    @Nullable
    List<@Nullable String> getStringList(@NotNull String path);

    @Nullable
    List<@Nullable Number> getNumberList(@NotNull String path);

    @Nullable
    JsonSection getJsonSection(@NotNull String path);

    boolean has(@NotNull String path);

    @Nullable
    Object get(@NotNull String path);

    void set(@NotNull String path, @Nullable Object data);

    @NotNull
    JsonObject toJsonObject();

    @Override
    String toString();

    @Nullable
    public static JsonSection readFromJson(@NotNull String json) {
        return new MemorySection(json);
    }

    @NotNull
    public static JsonSection createSection() {
        return new MemorySection();
    }
}
