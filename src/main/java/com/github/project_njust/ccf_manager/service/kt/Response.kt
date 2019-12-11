package com.github.project_njust.ccf_manager.service.kt

import com.github.project_njust.ccf_manager.service.IResponse
import com.github.project_njust.ccf_manager.wrapper.json.MemorySection

class Response(status: IResponse.Status = IResponse.Status.SUCCESS) : MemorySection(), IResponse {

    init {
        this.setStatus(status)
    }

    override fun setStatus(s: IResponse.Status) {
        super.set("status", s.key)
    }

}