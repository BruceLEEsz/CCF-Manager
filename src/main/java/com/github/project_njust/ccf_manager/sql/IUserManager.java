package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.wrapper.token.TokenKt;
import com.github.project_njust.ccf_manager.wrapper.token.TokenManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IUserManager {

    /**
     * 传入数据库之前的密码hash 以及后端校验密码用
     *
     * @param password
     * @return
     */
    @NotNull
    static String hashPassword(@NotNull String password) {
        return TokenKt.hashSHA256WithSalt(password, "pw", TokenManager.Signature);
    }

    /**
     * 创建学生信息, 用于学生第一次登入的时候
     *
     * @param studentid 学号
     * @return 用户信息 若为空则表示创建失败
     */
    @Nullable
    User createUser(@NotNull String studentid);

    /**
     * 创建管理员
     *
     * @param userName 管理员用户名
     * @param password 密码 请hash一次
     * @return 用户 如果返回为空 则表示名字已存在
     */
    @Nullable
    User createAdmin(@NotNull String userName, @NotNull String password);

    /**
     * 创建社团负责人
     *
     * @param userName 负责人用户名
     * @param password 密码 请hash一次
     * @return 用户 如果返回为空 则表示名字已存在
     */
    @Nullable
    User createPrincipal(@NotNull String userName, @NotNull String password);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    void updateUser(@NotNull User user);

    /**
     * 获取指定的用户
     *
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
     *      *
     * @param name 用户名
     * @return 见上
     */
    int getUID(@NotNull String name);

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    @NotNull
    List<@NotNull User> selectAllUser();

}
