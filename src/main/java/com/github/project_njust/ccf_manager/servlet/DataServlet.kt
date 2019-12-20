package com.github.project_njust.ccf_manager.servlet

import com.github.project_njust.ccf_manager.ContextManager
import com.github.project_njust.ccf_manager.SQLManager
import com.github.project_njust.ccf_manager.UserType
import com.github.project_njust.ccf_manager.model.User
import com.github.project_njust.ccf_manager.service.kt.CoroutinesService
import com.github.project_njust.ccf_manager.service.IResponse
import com.github.project_njust.ccf_manager.service.Service
import com.github.project_njust.ccf_manager.service.impl.*
import com.github.project_njust.ccf_manager.service.kt.SubmitData
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection
import com.github.project_njust.ccf_manager.wrapper.json.MemorySection
import com.github.project_njust.ccf_manager.wrapper.token.TokenManager
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
            val t = parms.getString("token")
            val token = t?.let { TokenManager.deToken(it) }
            if (UserType.TOURISTS !in service.allowTypes) {
                if (token == null) {
                    val res = IResponse.createIResponse(IResponse.Status.REFUSE)
                    res["reason"] = "权限不足"
                    val writer = resp.writer
                    writer.write(res.toString())
                    async.complete()
                    return@launch
                }
                var auth = false
                for(ut in service.allowTypes){
                    if(token.userType.typeId >= ut.typeId){
                        auth = true
                        break
                    }
                }
                if (!auth) {
                    val res = IResponse.createIResponse(IResponse.Status.REFUSE)
                    res["reason"] = "权限不足或权限不正确"
                    val writer = resp.writer
                    writer.write(res.toString())
                    async.complete()
                    return@launch
                }
            }
            val input = parms.getJsonSection("parms")!!
            val user: User?
            if (token?.uid != null) {
                user = SQLManager.getUserManager().selectUserById(token?.uid)
            } else {
                user = null
            }
            val submitData = SubmitData(input, user)
            val result = withTimeoutOrNull(5000) {
                if (service.isCoroutines) {
                    val cs = service as CoroutinesService
                    cs.onCoroutinesRequest(submitData)
                } else {
                    service.onRequest(submitData)
                }
            }
            val writer = resp.writer
            if (result == null) {
                writer.write("{\"status\":\"请求超时\"}")
            } else {
                if (token != null) {
                    result.set("token", token.toTokenString())
                }
                writer.write(result.toString())
            }
            async.complete()
        }
    }

    companion object {
        val services = mutableMapOf<String, Service>()
        fun init() {
            for (ser in listOf(
                    AddExam(),
                    Confirm(),
                    DownLoadFinalList(),
                    DownloadSignUpList(),
                    GetApplyList(),
                    GetScore(),
                    Login(),
                    SetCode(),
                    SetCompetition(),
                    SetQualification(),
                    SetScoreLine(),
                    SignUp(),
                    UpdateFinalList(),
                    UpdateStudentInfo(),
                    UpdateStudentScore()
            )){
                services["/${ser.name}"] = ser
            }
            println("DataServlet初始化完成")
        }
    }
}