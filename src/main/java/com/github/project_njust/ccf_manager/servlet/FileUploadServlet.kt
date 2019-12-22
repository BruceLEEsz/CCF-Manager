package com.github.project_njust.ccf_manager.servlet

import com.github.project_njust.ccf_manager.ContextManager
import com.github.project_njust.ccf_manager.FileManager
import com.github.project_njust.ccf_manager.service.IResponse
import com.github.project_njust.ccf_manager.wrapper.token.TokenManager
import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.FileItemFactory
import org.apache.commons.fileupload.ProgressListener
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import org.apache.commons.fileupload.servlet.ServletFileUpload
import org.apache.log4j.Logger
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap


@WebServlet(
        urlPatterns = ["/File/upload"],
        asyncSupported = true
)
class FileUploadServlet : HttpServlet() {

    val factory: FileItemFactory = DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, FileManager.tempFilesFolder)
    val fileUpload = ServletFileUpload(factory)

    companion object : ProgressListener {
        val cacheFiles = HashMap<UUID, File>()
        val cacheFileName = hashMapOf<UUID, String>()

        override fun update(pBytesRead: Long, pContentLength: Long, pItems: Int) {
        }

        fun init() {
        }

        fun createCacheFile(): Pair<UUID, File> {
            val uuid = UUID.randomUUID()
            val file = File(FileManager.tempFilesFolder, "$uuid")
            cacheFiles[uuid] = file
            return uuid to file
        }
    }

    init {
        fileUpload.fileSizeMax = ContextManager.getServletContext().getInitParameter("maxFileSize").toInt() * 1024 * 1024L
        fileUpload.sizeMax = fileUpload.fileSizeMax * 10
        fileUpload.progressListener = Companion
        Logger.getLogger(FileUploadServlet::class.java).info("Servlet初始化完成")
    }

    private fun parseFile(fileItem: FileItem): UUID? {
        if (fileItem.isFormField) {
            return null
        }
        val name = fileItem.name
        val uuid = UUID.randomUUID()
        val realFileName = "$uuid-$name"
        val file = File(FileManager.tempFilesFolder, realFileName)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        val fileStream = fileItem.inputStream
        val out = FileOutputStream(file)
        val buffer = ByteArray(1024)
        var len = -1;
        do {
            len = fileStream.read(buffer)
            if (len == -1) {
                break
            }
            out.write(buffer, 0, len)
        } while (true)
        out.flush()
        out.close()
        fileStream.close()
        cacheFiles[uuid] = file
        return uuid
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.characterEncoding = ContextManager.getEncoding()
        resp.status = 400
        resp.writer.println("错误 请用POST访问")
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        req.characterEncoding = ContextManager.getEncoding()
        if (!ServletFileUpload.isMultipartContent(req)) {
            resp.status = 400
            return
        }
        val forms = mutableListOf<FileItem>()
        val files = mutableListOf<FileItem>()
        for (fileItem in fileUpload.parseRequest(req)) {
            if (!fileItem.isFormField) {
                files += fileItem
            } else {
                forms += fileItem
            }
        }
        val token = forms.find {

            println("it.name: ${it.fieldName}")
            it.fieldName == "token"
        }
        println("token: $token")
        val result = IResponse.createIResponse(IResponse.Status.SUCCESS)
        if (token == null) {
            result.setStatus(IResponse.Status.REFUSE)
        } else {
            val t = token.getString("utf-8")
            println("t: $t")
            val token = TokenManager.deToken(t)
            if (token == null) {
                result.setStatus(IResponse.Status.REFUSE)
            } else {
                result.set("token", token.toTokenString())
                if (files.isEmpty()) {
                    result.setStatus(IResponse.Status.ERROR)
                    result["reason"] = "未找到上传的文件"
                } else {
                    val uuid = parseFile(files.first())
                    if (uuid == null) {
                        result.setStatus(IResponse.Status.ERROR)
                        result["reason"] = "上传失败"
                    } else {
                        result["UUID"] = uuid.toString()
                    }
                }
            }
        }
        resp.characterEncoding = ContextManager.getEncoding()
        resp.setHeader("Content-type", "application/json;charset=UTF-8");
        resp.writer.write(result.toString())
    }
}