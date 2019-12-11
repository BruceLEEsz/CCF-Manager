package com.github.project_njust.ccf_manager.service.kt

import com.github.project_njust.ccf_manager.UserType
import com.github.project_njust.ccf_manager.service.IResponse
import com.github.project_njust.ccf_manager.service.ISubmitData
import com.github.project_njust.ccf_manager.service.Service
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection

abstract class CoroutinesService(name: String, vararg types: UserType) : Service(name, *types) {


    abstract suspend fun onCoroutinesRequest(input: ISubmitData): IResponse
}