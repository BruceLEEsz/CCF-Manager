package com.github.project_njust.ccf_manager.sql.impl

import com.github.project_njust.ccf_manager.model.User
import com.github.project_njust.ccf_manager.sql.IUserManager

object UserManagerImpl : IUserManager {
    override fun createUser(studentid: String): User {
        TODO("not implemented")
    }

    override fun updateUser(user: User) {
        TODO("not implemented")
    }

    override fun selectUserById(uid: Int): User? {
        TODO("not implemented")
    }

    override fun getUID(name: String): Int {
        TODO("not implemented")
    }

    override fun selectAllUser(): MutableList<User> {
        TODO("not implemented")
    }
}