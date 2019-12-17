package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.User;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IUserManager {

    /**
     * 创建学生信息, 用于学生第一次登入的时候
     * @param studentid 学号
     * @return 用户信息
     */
    @NotNull
    User createUser(@NotNull String studentid);

    /**
     * 更新用户信息
     * @param user 用户信息
     */
    void updateUser(@NotNull User user);

    /**
     * 获取指定的用户
     * @param uid 用户id
     * @return 用户信息
     */
    @Nullable
    public User selectUserById(int uid);

    /**
     * 根据用户名获取用户信息.
     * 本方法会有以下几种情况:
     * [1]: 输入管理员用户名或社团用户名 返回对应的uid
     * [2]: 输入学号    若学生未登入将会返回-2 !!!
     * @param name 用户名
     * @return 见上
     */
    int getUID(@NotNull String name);

    /**
     * 获取所有用户
     * @return 所有用户
     */
    @NotNull
    public List<@NotNull User> selectAllUser();

}
