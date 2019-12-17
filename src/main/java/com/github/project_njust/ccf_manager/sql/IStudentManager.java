package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.Student;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IStudentManager {

    /**
     * 插入新的学生纪录
     * @param record 学生信息
     */
    void insertStudent(@NotNull Student record);

    /**
     * 更新学生信息
     * @param record 学生信息
     */
    void updateStudent(@NotNull Student record);

    /**
     * 删除指定的学生信息
     * @param StudentID 学号
     */
    void deleteStudent(@NotNull String StudentID);

    /**
     * 查询学生信息
     * @param StudentID 学号
     * @return 学生信息
     */
    @Nullable
    Student selectStudentByStudentID(@NotNull String StudentID);

    /**
     * 获取数据库里所有的学生
     * <strong>非必要请勿调用</strong>
     * @return 所有的学生信息
     */
    @NotNull
    List<@NotNull Student> selectStudents();

    /**
     * 根据指定的UID查询对应的学生信息.
     * 注: 对于没有登入过系统的学生将无法查到
     * @param uid 用户id
     * @return 学生信息
     */
    @Nullable
    Student selectStudent(int uid);

}
