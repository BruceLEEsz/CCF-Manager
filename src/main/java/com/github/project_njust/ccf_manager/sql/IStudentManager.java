package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.Student;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IStudentManager {
    int insertStudent(@NotNull Student record);

    int updateStudent(@NotNull Student record, String StudentID);

    int deleteStudent(@NotNull String StudentID);

    @Nullable
    Student selectStudentByStudentID(@NotNull String StudentID);

    @NotNull
    List<@NotNull Student> selectStudents();

    @Nullable
    Student selectStudent(int uid);

}
