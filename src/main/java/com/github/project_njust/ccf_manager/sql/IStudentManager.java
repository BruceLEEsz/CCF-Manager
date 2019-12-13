package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.Student;

import java.util.List;

public interface IStudentManager {
    int insertStudent(Student record);

    int updateStudent(Student record, String StudentID);

    int deleteStudent(String StudentID);

    Student selectStudentByStudentID(String StudentID);

    List<Student> selectStudents();

    Student selectStudent(int uid);

}
