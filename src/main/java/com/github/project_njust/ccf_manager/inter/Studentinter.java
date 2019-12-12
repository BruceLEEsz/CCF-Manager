package com.github.project_njust.ccf_manager.inter;

import com.github.project_njust.ccf_manager.model.Student;
import com.github.project_njust.ccf_manager.model.User;

import java.util.List;

public interface Studentinter {
    int insertStudent(Student record);

    public int updateStudent (Student record,String StudentID);

    public int deleteStudent(String StudentID);

    public Student selectStudentbyStudentID(String StudentID);

    public List<Student> selectStuent();

}
