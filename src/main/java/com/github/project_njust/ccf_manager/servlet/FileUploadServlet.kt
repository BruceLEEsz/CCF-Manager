package com.github.project_njust.ccf_manager.servlet

import com.github.project_njust.ccf_manager.ContextManager
import com.github.project_njust.ccf_manager.FileManager
import org.apache.commons.fileupload.FileItemFactory
import org.apache.commons.fileupload.ProgressListener
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import org.apache.commons.fileupload.servlet.ServletFileUpload
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

        override fun update(pBytesRead: Long, pContentLength: Long, pItems: Int) {
        }
        fun init(){
        }
    }

    init {
        fileUpload.fileSizeMax = ContextManager.getServletContext().getInitParameter("maxFileSize").toInt() * 1024 * 1024L
        fileUpload.sizeMax = fileUpload.fileSizeMax * 10
        fileUpload.progressListener = Companion
        println("Servlet初始化完成")
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.characterEncoding = ContextManager.getEncoding()
        resp.status = 400
        resp.writer.println("错误 请用POST访问")
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        println("接收信息")
        req.characterEncoding = ContextManager.getEncoding()
        if (!ServletFileUpload.isMultipartContent(req)) {
            resp.status = 400
            return
        }
        for (fileItem in fileUpload.parseRequest(req)) {
            if (!fileItem.isFormField) {
                val name = fileItem.name
                val uuid = UUID.randomUUID()
                val realFileName = "$uuid-$name"
                //创建要保存的文件
                val file = File(FileManager.tempFilesFolder, realFileName);
                //判断文件夹是否存在
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                val fileStream = fileItem.getInputStream();
                val out = FileOutputStream(file);
                val buffer = ByteArray(1024)
                var len = -1;
                do {
                    len = fileStream.read(buffer)
                    if (len == -1) {
                        break
                    }
                    out.write(buffer, 0, len);
                } while (true)
                out.flush();
                //关闭流
                out.close();
                fileStream.close();
                cacheFiles[uuid] = file
                TODO()
            } else {
                val fieldName = fileItem.getFieldName();
                val fieldValue = fileItem.getString();
                System.out.println("$fieldName:$fieldValue");
            }
        }

    }
}