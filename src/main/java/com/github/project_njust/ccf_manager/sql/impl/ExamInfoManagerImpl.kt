package com.github.project_njust.ccf_manager.sql.impl

import com.github.project_njust.ccf_manager.SQLManager
import com.github.project_njust.ccf_manager.model.ExamInfo
import com.github.project_njust.ccf_manager.sql.IExamInfoManager
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection
import kotlinx.coroutines.runBlocking
import java.sql.Date

object ExamInfoManagerImpl : IExamInfoManager {
    override fun createExamInformation(score: Int, date: Date): ExamInfo = runBlocking {
        SQLManager.coroutinesConnection {
            val ps = this.prepareStatement("INSERT INTO ExamInformation (EXAMSCORE, EXAMDATE, DATA) VALUES (?, ?, ?)")
            ps.setInt(1, score)
            ps.setDate(2, date)
            ps.setString(3, "{}")
            ps.executeUpdate()
        }
        return@runBlocking getLatestInfo()
    }

    override fun getLastInfo(): ExamInfo? {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("SELECT * FROM ExamInformation WHERE EXAMID IN (SELECT MAX(EXAMID) FROM ExamInformation)")
            val rs = ps.executeQuery()
            if (rs.next()) {
                return ExamInfo(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getDate(3),
                        rs.getString(4),
                        JsonSection.readFromJson(rs.getString(5))
                )
            }
        }
        return null
    }

    private suspend fun getLatestInfo(): ExamInfo {
        return SQLManager.asyncDeferred {
            val ps = this.prepareStatement("SELECT * FROM ExamInformation WHERE EXAMID IN (SELECT MAX(EXAMID) FROM ExamInformation)")
            val rs = ps.executeQuery()
            if (rs.next()) {
                ExamInfo(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getDate(3),
                        rs.getString(4),
                        JsonSection.readFromJson(rs.getString(5))
                )
            } else {
                throw IllegalStateException("查找不到考试信息")
            }
        }.await()!!
    }

    override fun updateExamInformation(ei: ExamInfo) {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("UPDATE ExamInformation SET EXAMSCORE = ?, EXAMDATE = ?, DATA = ?, CODE = ? WHERE EXAMID = ?")
            ps.setInt(1, ei.examscore)
            ps.setDate(2, ei.examdate)
            ps.setString(3, ei.data.toString())
            ps.setString(4, ei.code)
            ps.setInt(5, ei.examid)
            ps.executeUpdate()
        }
    }

    override fun selectExamInformationByExamId(ExamId: Int): ExamInfo? {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("SELECT * FROM ExamInformation WHERE EXAMID = ?")
            ps.setInt(1, ExamId)
            val rs = ps.executeQuery()
            if (rs.next()) {
                return ExamInfo(ExamId, rs.getInt(2), rs.getDate(3), rs.getString(4), JsonSection.readFromJson(rs.getString(5)))
            }
        }
        return null
    }
}