package com.github.project_njust.ccf_manager.sql.impl

import com.github.project_njust.ccf_manager.model.Student
import com.github.project_njust.ccf_manager.sql.IStudentManager

object StudentManagerImpl : IStudentManager {
    override fun insertStudent(record: Student) {
        TODO("not implemented")
    }

    override fun updateStudent(record: Student) {
        TODO("not implemented")
    }

    override fun deleteStudent(StudentID: String) {
        TODO("not implemented")
    }

    override fun selectStudents(): MutableList<Student> {
        TODO("not implemented")
    }

    override fun selectStudent(uid: Int): Student? {
        TODO("not implemented")
    }

    override fun selectStudentByStudentID(StudentID: String): Student? {
        TODO("not implemented")
    }
}