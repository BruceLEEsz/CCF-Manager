package com.github.project_njust.ccf_manager.inter.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.inter.ExamInformationinter;
import com.github.project_njust.ccf_manager.model.Examinformation;
import com.github.project_njust.ccf_manager.model.User;

import java.sql.*;
import java.util.List;

public class ExamInformationimpl implements ExamInformationinter {

    @Override
    public int insertExamInformation(Examinformation record) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try{
            String sql = "insert into examinformation(ExamID,ExamScore,ExamDate) value(?,?,?)";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, record.getExamid());
            pst.setInt(2, record.getExamscore());
            pst.setDate(3, (Date) record.getExamdate());
            int rows = pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return 0;
    }

    @Override
    public int updateExamInformation(Examinformation record, int ExamID) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "update examinformation set ExamDate=?,ExamScore=? where ExamID=?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,record.getExamid());
            pst.setInt(2, record.getExamscore());
            pst.setDate(3, (Date) record.getExamdate());
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return 0;
    }

    @Override
    public int deleteExamInformation(int ExamId) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "delete from examinformation where ExamID = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,ExamId);
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Examinformation selectExamInformationByExamId(int ExamId) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "select * from examinformation where ExamID = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, ExamId);
            ResultSet rows = pst.executeQuery();
            if(rows.next()){
                Examinformation ei = new Examinformation();
                ei.setExamid(rows.getInt("ExamID"));

                return ei;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> selectAllUser() {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "select * from examinformation";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rows = pst.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
