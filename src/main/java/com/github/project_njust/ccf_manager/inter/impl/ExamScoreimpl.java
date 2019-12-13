package com.github.project_njust.ccf_manager.inter.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.inter.ExamScoreinter;
import com.github.project_njust.ccf_manager.model.Examscore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ExamScoreimpl implements ExamScoreinter {

    @Override
    public int insertExamScore(Examscore examscore) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try{
            String sql = "insert into examscore(Confirm,Competition,ExamGrade,ExamID) value(?,?,?,?)";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, examscore.getConfirm());
            pst.setString(2, examscore.getCompetition());
            pst.setInt(3, examscore.getExamgrade());
            pst.setInt(4,examscore.getExamid());
            int rows = pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return 0;
    }

    @Override
    public int updateExamScore(Examscore examscore, int uid) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "update examscore set Confirm=?,Competition=?,ExamGrade=?,ExamID=? where UID=?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,examscore.getConfirm());
            pst.setString(2,examscore.getCompetition());
            pst.setInt(3,examscore.getExamgrade());
            pst.setInt(4,examscore.getExamid());
            pst.setInt(5, examscore.getUid());
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return 0;
    }

    @Override
    public int deleteExamScore(int uid) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "delete from examscore where UID = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, uid);
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Examscore selectExamScoreById(int uid) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "select * from examscore where UID = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, uid);
            ResultSet rows = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Examscore> selectAllExamScore() {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "select * from examscore";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rows = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
