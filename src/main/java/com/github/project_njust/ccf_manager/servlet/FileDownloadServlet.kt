package com.github.project_njust.ccf_manager.servlet

import com.github.project_njust.ccf_manager.ContextManager
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URLEncoder
import java.util.*
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(
        urlPatterns = ["/File/download"],
        asyncSupported = true
)
class FileDownloadServlet : HttpServlet() {

    val files = mutableMapOf<String, String>()

    init {
        files += "student_template" to "学生信息模板.xls"
    }

    private fun OutputStream.write(ins: InputStream) {
        val out = this
        val buffer = ByteArray(1024)
        while (true) {
            val len = ins.read(buffer)
            if (len == -1) {
                break
            }
            out.write(buffer, 0, len)
        }
        out.flush()
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.characterEncoding = ContextManager.getEncoding()
        val fileName = req.getParameter("file")
        resp.characterEncoding = ContextManager.getEncoding()
        if (fileName == null) {
            resp.status = 404
            return
        }
        if (files.containsKey(fileName)) {
            val name = files[fileName]!!
            resp.setHeader("Content-Disposition", "attachment;filename=${URLEncoder.encode(name,"UTF-8")}");
            val ins = Thread.currentThread().contextClassLoader.getResourceAsStream(name)
            resp.status = 200
            val out = resp.outputStream
            out.write(ins!!)
            out.close()
            ins.close()
        } else {
            try {
                val uuid = UUID.fromString(fileName)
                val file = FileUploadServlet.cacheFiles[uuid]
                if(file == null){
                    resp.status = 404
                    return
                }
                resp.setHeader("Content-Disposition", "attachment;filename=$uuid");
                val ins = FileInputStream(file)
                val out = resp.outputStream
                out.write(ins)
                out.close()
                ins.close()
            } catch (e: Throwable) {
                resp.status = 404
                return
            }
        }
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        doGet(req, resp)
    }
}