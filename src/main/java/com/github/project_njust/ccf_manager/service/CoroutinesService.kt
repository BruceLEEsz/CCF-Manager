package com.github.project_njust.ccf_manager.service

import com.github.project_njust.ccf_manager.UserType
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection

abstract class CoroutinesService(name: String, vararg types: UserType) : Service(name, *types) {


    abstract suspend fun onCoroutinesRequest(input: JsonSection): JsonSection
}