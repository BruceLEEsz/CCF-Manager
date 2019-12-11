package com.github.project_njust.ccf_manager.servlet

import com.github.project_njust.ccf_manager.ContextManager
import com.github.project_njust.ccf_manager.service.kt.CoroutinesService
import com.github.project_njust.ccf_manager.service.ExampleService
import com.github.project_njust.ccf_manager.service.Service
import com.github.project_njust.ccf_manager.service.kt.SubmitData
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection
import com.github.project_njust.ccf_manager.wrapper.json.MemorySection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.io.InputStreamReader
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(
        urlPatterns = ["/Data/*"],
        asyncSupported = true
)
class DataServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.characterEncoding = ContextManager.getEncoding()
        resp.status = 400
        resp.writer.println("错误 请用POST访问")
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        req.characterEncoding = ContextManager.getEncoding()
        val data = MemorySection.parser.parse(InputStreamReader(req.inputStream)).asJsonObject
        val path = req.pathInfo
        println("path:$path")
        val service = services[path]
        if (service == null) {
            resp.status = 404
            return
        }
        val async = req.startAsync(req, resp)
        GlobalScope.launch {
            resp.characterEncoding = ContextManager.getEncoding()
            resp.setHeader("Content-type", "application/json;charset=UTF-8");
            val resp = async.response
            resp.characterEncoding = ContextManager.getEncoding()
            val parms = MemorySection(data)
            val token = parms.getString("token")
            val input = parms.getJsonSection("parms")!!
            val submitData = SubmitData(input)
            val result = withTimeoutOrNull(5000) {
                if (service.isCoroutines) {
                    val cs = service as CoroutinesService
                    cs.onCoroutinesRequest(submitData)
                } else {
                    service.onRequest(submitData)
                }
            }
            val respone = JsonSection.createSection()
            respone.set("token", "test")
            if (result != null)
                respone.set("result", result)
            val writer = resp.writer
            if (result == null) {
                writer.write("{\"status\":\"请求超时\"}")
            } else {
                writer.write(respone.toString())
            }
            async.complete()
        }
    }

    companion object {
        val services = mutableMapOf<String, Service>()
        fun init() {
            services["/example"] = ExampleService()

            println("DataServlet初始化完成")
        }
    }
}