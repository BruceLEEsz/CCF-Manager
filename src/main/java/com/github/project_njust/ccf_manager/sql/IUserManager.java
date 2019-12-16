package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.User;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IUserManager {

    @NotNull
    public User createUser(@NotNull String studentid);

    public int updateUser(@NotNull User user, int uid);

    public int deleteUser(int uid);

    @Nullable
    public User selectUserById(int uid);

    @Nullable
    User selectUserByName(@NotNull String name);

    @NotNull
    public List<@NotNull User> selectAllUser();

}
