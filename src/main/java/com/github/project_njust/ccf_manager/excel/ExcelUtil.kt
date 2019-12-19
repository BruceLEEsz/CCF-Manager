package com.github.project_njust.ccf_manager.excel

import com.github.project_njust.ccf_manager.FileManager
import com.github.project_njust.ccf_manager.model.ExamScore
import com.github.project_njust.ccf_manager.model.Student
import com.github.project_njust.ccf_manager.servlet.FileUploadServlet
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection
import org.apache.log4j.Logger
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

object ExcelUtil {


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
        val book = HSSFWorkbook(FileInputStream(file))
        val she = book.getSheetAt(0)
        for (index in 1..she.lastRowNum) {
            val row = she.getRow(index)
            if (row?.getCell(0) == null || row.getCell(0).stringCellValue.isEmpty()) {
                continue
            }
            try {
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
        val book = HSSFWorkbook(FileInputStream(file))
        val she = book.getSheetAt(0)
        for (index in 3..she.lastRowNum) {
            val row = she.getRow(index)
            if (row?.getCell(0) == null || row.getCell(0).stringCellValue.isEmpty()) {
                continue
            }
            try {
                val type = row.getCell(0).getString()
                if (type != "身份证") {
                    Logger.getLogger(ExcelUtil::class.java).warn("")
                    continue
                }
            } catch (e: Throwable) {
            }
        }
        return list
    }

}