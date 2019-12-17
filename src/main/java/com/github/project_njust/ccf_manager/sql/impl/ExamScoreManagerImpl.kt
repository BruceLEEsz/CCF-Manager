package com.github.project_njust.ccf_manager.sql.impl

import com.github.project_njust.ccf_manager.SQLManager
import com.github.project_njust.ccf_manager.model.ExamScore
import com.github.project_njust.ccf_manager.sql.IExamScoreManager
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection

object ExamScoreManagerImpl : IExamScoreManager {

    override fun selectExamScoreByUser(uid: Int): MutableList<ExamScore> {
        val list = mutableListOf<ExamScore>()
        SQLManager.operateConnection {
            val ps = this.prepareStatement("SELECT EXAMID FROM ExamScore WHERE UID = ?")
            ps.setInt(1, uid)
            val rs = ps.executeQuery()
            while (rs.next()) {
                val eid = rs.getInt(1)
                list += selectExamScore(uid, eid) ?: continue
            }
        }
        return list
    }

    override fun selectExamScore(uid: Int, examid: Int): ExamScore? {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("SELECT * FROM ExamScore WHERE UID = ? AND EXAMID = ? LIMIT 1")
            ps.setInt(1, uid)
            ps.setInt(2, examid)
            val rs = ps.executeQuery()
            if (rs.next()) {
                return ExamScore(
                        uid,
                        examid,
                        rs.getBoolean(3),
                        rs.getInt(4),
                        JsonSection.readFromJson(rs.getString(5))
                )
            }
        }
        return null
    }

    override fun selectAllExamScore(examid: Int): MutableList<ExamScore> {
        val list = mutableListOf<ExamScore>()
        SQLManager.operateConnection {
            val ps = this.prepareStatement("SELECT UID FROM ExamScore WHERE EXAMID = ?")
            ps.setInt(1, examid)
            val rs = ps.executeQuery()
            while (rs.next()) {
                val uid = rs.getInt(1)
                list += selectExamScore(uid, examid) ?: continue
            }
        }
        return list
    }

    override fun updateExamScore(es: ExamScore) {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("UPDATE ExamScore SET CONFIRM = ?, EXAMGRADE = ?, DATA = ? WHERE UID = ? AND EXAMID = ?")
            ps.setBoolean(1, es.confirm)
            ps.setInt(2, es.examgrade)
            ps.setString(3, es.data.toString())
            ps.setInt(4, es.uid)
            ps.setInt(5, es.examid)
            ps.executeUpdate()
        }
    }

    override fun insertExamScore(es: ExamScore) {
        SQLManager.operateConnection {
            val ps = this.prepareStatement("INSERT INTO ExamScore VALUES (?, ?, ?, ?, ?)")
            ps.setInt(1, es.uid)
            ps.setInt(2, es.examid)
            ps.setBoolean(3, es.confirm)
            ps.setInt(4, es.examgrade)
            ps.setString(5, es.data.toString())
        }
    }

    override fun selectConditional(sql: String, vararg args: Any): MutableList<ExamScore> {
        if (!sql.startsWith("SELECT * FROM ExamScore")) {
            throw IllegalArgumentException("指定的查询语句并非以SELECT * FROM ExamScore开头, 错误的语句: $sql")
        }
        val list = mutableListOf<ExamScore>()
        SQLManager.operateConnection {
            val ps = this.prepareStatement(sql)
            for ((i, v) in args.withIndex()) {
                ps.setObject(i + 1, v)
            }
            val rs = ps.executeQuery()
            while (rs.next()) {
                list += ExamScore(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getBoolean(3),
                        rs.getInt(4),
                        JsonSection.readFromJson(rs.getString(5))
                )
            }
        }
        return list
    }
}