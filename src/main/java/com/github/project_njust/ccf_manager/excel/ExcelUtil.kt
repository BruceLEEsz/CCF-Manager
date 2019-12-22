package com.github.project_njust.ccf_manager.excel

import com.github.project_njust.ccf_manager.SQLManager
import com.github.project_njust.ccf_manager.model.ExamScore
import com.github.project_njust.ccf_manager.model.Student
import com.github.project_njust.ccf_manager.servlet.FileUploadServlet
import com.github.project_njust.ccf_manager.sql.impl.StudentManagerImpl
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection
import org.apache.log4j.Logger
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.*
import java.util.*

object ExcelUtil {

    private fun loadBook(file: File): Workbook {
        try {
            return XSSFWorkbook(file)
        }catch (e:Throwable){
            return HSSFWorkbook(FileInputStream(file))
        }
    }

    private fun loadBook(file: InputStream): Workbook {
        try {
            return XSSFWorkbook(file)
        }catch (e:Throwable){
            return HSSFWorkbook(file)
        }
    }
    private fun Cell.getString(): String {
        return when (cellType) {
            CellType.STRING -> stringCellValue
            CellType.NUMERIC -> {
                val l = this.numericCellValue.toLong()
                val diff = Math.abs(l - numericCellValue)
                if (diff < 1e-6) {
                    return l.toString()
                }
                return numericCellValue.toString()
            }
            else -> toString()
        }
    }

    private fun Cell.getInt(): Int? {
        return when (cellType) {
            CellType.STRING -> {
                try {
                    stringCellValue.toInt()
                } catch (e: Throwable) {
                    null
                }
            }
            CellType.NUMERIC -> {
                numericCellValue.toInt()
            }
            else -> null
        }
    }

    private fun Cell.getDouble(): Double? {
        return when (cellType) {
            CellType.STRING -> {
                try {
                    stringCellValue.toDouble()
                } catch (e: Throwable) {
                    null
                }
            }
            CellType.NUMERIC -> {
                numericCellValue
            }
            else -> null
        }
    }

    @JvmStatic
    @Throws(Throwable::class)
    fun loadStudents(uuid: UUID): List<Student> {
        val list = mutableListOf<Student>()
        val file = FileUploadServlet.cacheFiles[uuid] ?: throw FileNotFoundException("找不到文件")
        val book = loadBook(file)
        val she = book.getSheetAt(0)
        for (index in 1..she.lastRowNum) {
            val row = she.getRow(index)
            try {
                if (row?.getCell(0) == null || row.getCell(0).stringCellValue.isEmpty()) {
                    continue
                }
                val stu = Student(row.getCell(0).getString().toUpperCase(), JsonSection.createSection(), row.getCell(2).getString().toUpperCase())
                val json = stu.data
                json["Name"] = row.getCell(1).getString()
                json["EntryYear"] = row.getCell(3).getInt()
                list += stu
            } catch (e: Throwable) {
            }
        }
        return list
    }

    @JvmStatic
    @Throws(Throwable::class)
    fun loadScore(uuid: UUID): List<ExamScore> {
        val list = mutableListOf<ExamScore>()
        val file = FileUploadServlet.cacheFiles[uuid] ?: throw FileNotFoundException("找不到文件")
        val book = loadBook(file)
        val she = book.getSheetAt(0)
        val last = SQLManager.getExamInfoManager().lastInfo
                ?: throw IllegalStateException("错误 还没有任何进行中的考试")
        for (index in 3..she.lastRowNum) {
            val row = she.getRow(index)
            if (row?.getCell(0) == null ) {
                continue
            }
            try {
                val type = row.getCell(2).getString()
                if (type != "身份证") {
                    Logger.getLogger(ExcelUtil::class.java).warn("有学生提交了非身份证信息")
                    continue
                }
                val name = row.getCell(1).getString()
                val idc = row.getCell(3).getString().toUpperCase()
                println("idc: $idc")
                val score = row.getCell(11).getInt()!!
                println("score: $score")
                val iscorew = Array<Int>(5) {
                    row.getCell(12 + it).getInt()!!
                }
                val stu = StudentManagerImpl.selectConditional("SELECT * FROM Student WHERE IDENTITYCRAD = ? LIMIT 1", idc)
                        .firstOrNull() ?: continue
                var s = SQLManager.getExamScoreManager().selectExamScore(stu.uid, last.examid)
                if (s == null) {
                    s = ExamScore(stu.uid, last.examid, true, score, JsonSection.createSection())
                    SQLManager.getExamScoreManager().insertExamScore(s)
                }
                s.examgrade = score
                val json = s.data
                json.set(ExamScore.DETAILED_SCORE, listOf(*iscorew))
                SQLManager.getExamScoreManager().updateExamScore(s)
                list += s
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        return list
    }

    @JvmStatic
    fun createSignUpList(examid: Int): UUID {
        val (uuid, file) = FileUploadServlet.createCacheFile()
        val book = XSSFWorkbook()
        val sheet = book.createSheet("学生报名表")
        sheet.createRow(0).also {
            it.createCell(0).setCellValue("学号")
            it.createCell(1).setCellValue("姓名")
            it.createCell(2).setCellValue("身份证")
        }
        var index = 1
        val list = SQLManager.getExamScoreManager().selectAllExamScore(examid)
        for (es in list) {
            val stu = SQLManager.getStudentManager().selectStudent(es.uid) ?: continue
            val row = sheet.createRow(index++)
            row.createCell(0).setCellValue(stu.studentId)
            row.createCell(1).setCellValue(stu.data.getString("Name"))
            row.createCell(2).setCellValue(stu.identitycard)
        }
        val fo = FileOutputStream(file)
        book.write(fo)
        fo.flush()
        fo.close()
        FileUploadServlet.cacheFileName[uuid] = "报名名单.xlsx"
        return uuid
    }

    @JvmStatic
    @Throws(Throwable::class)
    fun loadFinalList(uuid: UUID) {
        val file = FileUploadServlet.cacheFiles[uuid] ?: throw FileNotFoundException("找不到文件")
        val book = loadBook(file)
        val sheet = book.getSheetAt(0)
        val last = SQLManager.getExamInfoManager().lastInfo
                ?: throw IllegalStateException("错误 还没有任何进行中的考试")
        for (index in 1..sheet.lastRowNum) {
            val row = sheet.getRow(index)
            try {
                if (row?.getCell(0) == null || row.getCell(0).stringCellValue.isEmpty()) {
                    continue
                }
                val sid = row.getCell(0).getString()
                val stu = SQLManager.getStudentManager().selectStudentByStudentID(sid) ?: continue
                val es = SQLManager.getExamScoreManager().selectExamScore(stu.uid, last.examid) ?: continue
                es.confirm = true
                SQLManager.getExamScoreManager().updateExamScore(es)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun createFinalList():UUID{
        val last = SQLManager.getExamInfoManager().lastInfo
                ?: throw IllegalStateException("错误 还没有任何进行中的考试")
        val (uuid, file) = FileUploadServlet.createCacheFile()
        val ins = Thread.currentThread().contextClassLoader.getResourceAsStream("红名单导入.xls")!!
        val book = HSSFWorkbook(ins)
        val sheet = book.getSheetAt(0)
        val list = SQLManager.getExamScoreManager().selectAllExamScore(last.examid)
        var index = 1
        for(es in list){
            if(!es.confirm){
                continue
            }
            val stu = SQLManager.getStudentManager().selectStudent(es.uid) ?: continue
            val row = sheet.getRow(index) ?: sheet.createRow(index)
            row.createCell(0).setCellValue(stu.data.getString("Name"))
            row.createCell(1).setCellValue(stu.identitycard)
            row.createCell(2).setCellValue(0.0)
            index++
        }
        val fo = FileOutputStream(file)
        book.write(fo)
        fo.flush()
        fo.close()
        FileUploadServlet.cacheFileName[uuid] = "红名单导入.xls"
        return uuid
    }

}