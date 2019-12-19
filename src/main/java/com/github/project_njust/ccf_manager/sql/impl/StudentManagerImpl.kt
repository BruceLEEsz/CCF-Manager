package com.github.project_njust.ccf_manager.sql.impl

import com.github.project_njust.ccf_manager.SQLManager
import com.github.project_njust.ccf_manager.model.Student
import com.github.project_njust.ccf_manager.sql.IStudentManager
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection

object StudentManagerImpl : IStudentManager {
    override fun insertStudent(s: Student) {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("INSERT INTO Student VALUES (?, ?, ?, ?)")
            ps.setString(1, s.studentId)
            ps.setInt(2, s.uid)
            ps.setString(3, s.identitycard)
            ps.setString(4, s.data.toString())
            ps.executeUpdate()
        }
    }

    override fun updateStudent(s: Student) {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("UPDATE Student SET UID = ?, DATA = ?, IDENTITYCRAD = ? WHERE STUDENTID = ?")
            ps.setInt(1, s.uid)
            ps.setString(2, s.data.toString())
            ps.setString(3, s.identitycard)
            ps.setString(4, s.studentId)
            ps.executeUpdate()
        }
    }

    override fun deleteStudent(sid: String) {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("DELETE FROM Student WHERE STUDENTID = ?")
            ps.setString(1, sid)
            ps.executeUpdate()
        }
    }

    override fun selectStudents(): MutableList<Student> {
        val list = mutableListOf<Student>()
        SQLManager.operateConnection {
            val ps = this.prepareStatement("SELECT * FROM Student")
            val rs = ps.executeQuery()
            while (rs.next()) {
                list += Student(
                        rs.getString(1),
                        rs.getInt(2),
                        JsonSection.readFromJson(rs.getString(4)),
                        rs.getString(3)
                )
            }
        }
        return list
    }

    override fun selectStudent(uid: Int): Student? {
        if (uid < 0) {
            return null
        }
        SQLManager.operateConnection {
            val ps = this.prepareStatement("SELECT * FROM Student WHERE UID = ? LIMIT 1")
            ps.setInt(1, uid)
            val rs = ps.executeQuery()
            if (rs.next()) {
                return Student(
                        rs.getString(1),
                        uid,
                        JsonSection.readFromJson(rs.getString(4)),
                        rs.getString(3)
                )
            }
        }
        return null
    }

    override fun selectStudentByStudentID(StudentID: String): Student? {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("SELECT * FROM Student WHERE STUDENTID = ? LIMIT 1")
            ps.setString(1, StudentID)
            val rs = ps.executeQuery()
            if (rs.next()) {
                return Student(
                        StudentID,
                        rs.getInt(2),
                        JsonSection.readFromJson(rs.getString(4)),
                        rs.getString(3)
                )
            }
        }
        return null
    }

    fun selectConditional(sql: String, vararg args: Any): MutableList<Student> {
        if (!sql.startsWith("SELECT * FROM Student")) {
            throw IllegalArgumentException("指定的查询语句并非以SELECT * FROM Student, 错误的语句: $sql")
        }
        val list = mutableListOf<Student>()
        SQLManager.operateConnection {
            val ps = this.prepareStatement(sql)
            for ((i, v) in args.withIndex()) {
                ps.setObject(i + 1, v)
            }
            val rs = ps.executeQuery()
            while (rs.next()) {
                list += Student(
                        rs.getString(1),
                        rs.getInt(2),
                        JsonSection.readFromJson(rs.getString(4)),
                        rs.getString(3)
                )
            }
        }
        return list
    }
}