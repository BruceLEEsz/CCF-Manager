package com.github.project_njust.ccf_manager.sql.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.sql.IExamInfoManager;

import java.sql.*;

public class ExamInfoManagerimpl implements IExamInfoManager {
    @Override
    public int insertExamInformation(ExamInfo record) {
        Connection conn= SQLManager.getConnection();
        int result = 0;
        PreparedStatement pst = null;
        try {
            String sql = "insert into examinformation(ExamScore, ExamDate, Data)  value(?,?, '{}')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,record.getExamscore());
            pst.setDate(2, (Date) record.getExamdate());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return result;
    }

    @Override
    public int updateExamInformation(ExamInfo record, int ExamID) {
        Connection conn= SQLManager.getConnection();
        int result=0;
        PreparedStatement pst = null;
        try {
            String sql = "update examinformation set ExamScore=?,ExamDate=?,Data=? where ExamID=?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,record.getExamscore());
            pst.setDate(2, (Date) record.getExamdate());
//TODO data
            pst.setInt(4,record.getExamid());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return result;
    }

    @Override
    public int deleteExamInformation(int ExamId) {
        Connection conn= SQLManager.getConnection();
        int result=0;
        PreparedStatement pst = null;
        try {
            String sql = "delete from examinformation where ExamID = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,ExamId);
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return result;
    }

    @Override
    public ExamInfo selectExamInformationByExamId(int ExamId) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        ResultSet result=null;
        try {
            String sql = "select * from examinformation where ExamId= ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,ExamId);
            result = pst.executeQuery();
            if(result.next()){
                return new ExamInfo(result.getInt("ExamID"), result.getInt("examscore"),result.getDate("examsdate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return null;
    }
}
