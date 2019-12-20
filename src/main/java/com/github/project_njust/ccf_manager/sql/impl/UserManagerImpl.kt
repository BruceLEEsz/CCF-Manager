package com.github.project_njust.ccf_manager.sql.impl

import com.github.project_njust.ccf_manager.SQLManager
import com.github.project_njust.ccf_manager.UserType
import com.github.project_njust.ccf_manager.model.User
import com.github.project_njust.ccf_manager.sql.IUserManager
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection

object UserManagerImpl : IUserManager {

    override fun updateUser(user: User) {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("UPDATE User SET PASSWORD = ?, `TYPE` = ?, DATA = ? WHERE UID = ?")
            ps.setString(1, user.password)
            ps.setInt(2, user.type)
            ps.setString(3, user.data.toString())
            ps.setInt(4, user.uid)
            ps.executeUpdate()
        }
    }

    override fun selectUserById(uid: Int): User? {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("SELECT * FROM User WHERE UID = ?")
            ps.setInt(1, uid)
            val rs = ps.executeQuery()
            if (rs.next()) {
                return User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        JsonSection.readFromJson(rs.getString(5))
                )
            }
        }
        return null
    }

    override fun createUser(studentid: String): User? {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("INSERT INTO User (PASSWORD, NAME, `TYPE`, DATA) VALUES (?, ?, ?, ?)")
            ps.setString(1, IUserManager.hashPassword(studentid))
            ps.setString(2, studentid)
            ps.setInt(3, UserType.STUDENT.typeId)
            ps.setString(4, "{}")
            ps.executeUpdate()
        }
        val uid = getUID(studentid)
        if (uid >= 0) {
            val student = SQLManager.getStudentManager().selectStudentByStudentID(studentid)
            if (student != null) {
                student.uid = uid
                SQLManager.getStudentManager().updateStudent(student)
            }
            return selectUserById(uid)
        }
        return null
    }

    override fun createPrincipal(userName: String, password: String): User? {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("INSERT INTO User (PASSWORD, NAME, `TYPE`, DATA) VALUES (?, ?, ?, ?)")
            ps.setString(1, password)
            ps.setString(2, userName)
            ps.setInt(3, UserType.PRINCIPAL.typeId)
            ps.setString(4, "{}")
            ps.executeUpdate()
        }
        val uid = getUID(userName)
        if (uid >= 0) {
            return selectUserById(uid)
        }
        return null
    }

    override fun createAdmin(userName: String, password: String): User? {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("INSERT INTO User (PASSWORD, NAME, `TYPE`, DATA) VALUES (?, ?, ?, ?)")
            ps.setString(1, password)
            ps.setString(2, userName)
            ps.setInt(3, UserType.ADMIN.typeId)
            ps.setString(4, "{}")
            ps.executeUpdate()
        }
        val uid = getUID(userName)
        if (uid >= 0) {
            return selectUserById(uid)
        }
        return null
    }

    override fun getUID(name: String): Int {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("SELECT UID FROM User WHERE NAME = ? LIMIT 1")
            ps.setString(1, name)
            val rs = ps.executeQuery()
            if (rs.next()) {
                return rs.getInt(1)
            }
        }
        val s = SQLManager.getStudentManager().selectStudentByStudentID(name)
        if (s != null) {
            if (s.uid == -1) {
                return -2
            }
            return s.uid
        }
        return -1
    }

    override fun selectAllUser(): MutableList<User> {
        val list = mutableListOf<User>()
        SQLManager.operateConnection {
            val ps = this.prepareStatement("SELECT * FROM User")
            val rs = ps.executeQuery()
            while (rs.next()) {
                list += User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        JsonSection.readFromJson(rs.getString(5))
                )
            }
        }
        return list
    }
}