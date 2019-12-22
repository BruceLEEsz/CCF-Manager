package com.github.project_njust.ccf_manager.service.kt

import com.github.project_njust.ccf_manager.model.User
import com.github.project_njust.ccf_manager.service.ISubmitData
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection

class SubmitData(
        private val data: JsonSection,
        private val user: User? = null
) : ISubmitData {
    override fun getUser(): User? = user

    override fun getData(): JsonSection = data
}