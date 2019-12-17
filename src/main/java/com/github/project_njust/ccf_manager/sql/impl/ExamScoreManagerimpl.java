package com.github.project_njust.ccf_manager.sql.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.model.Examscore;
import com.github.project_njust.ccf_manager.sql.IExamScoreManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamScoreManagerimpl implements IExamScoreManager {
    @Override
    public int insertExamScore(@NotNull Examscore examscore) {
        Connection conn= SQLManager.getConnection();
        int result = 0;
        PreparedStatement pst = null;
        try {
            String sql = "insert into examscore(Confirm, Competition, Data)  value(?,?, '{}')";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,examscore.getConfirm());
            pst.setString(2,examscore.getCompetition());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return result;
    }

    @Override
    public int updateExamScore(@NotNull Examscore examscore, int uid) {
        Connection conn= SQLManager.getConnection();
        int result=0;
        PreparedStatement pst = null;
        try {
            String sql = "update examscore set Confirm=?,Competition=?,Data=? where UID=?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,examscore.getConfirm());
            pst.setString(2, examscore.getCompetition());
//TODO data
            pst.setInt(4,examscore.getUid());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return result;
    }

    @Override
    public int deleteExamScore(int uid) {
        Connection conn= SQLManager.getConnection();
        int result=0;
        PreparedStatement pst = null;
        try {
            String sql = "delete from examscore where Uid = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,uid);
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return result;
    }

    @Override
    public @Nullable Examscore selectExamScoreById(int uid) {
        Connection conn= SQLManager.getConnection();
        ResultSet result=null;
        PreparedStatement pst = null;
        try {
            String sql = "select * from examscore where UID= ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,uid);
            result = pst.executeQuery();
            if(result.next()){
                return new Examscore(result.getInt("uid"),result.getInt("examid"), result.getInt("Confirm"), result.getInt("examgrade"),result.getString("competition"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return null;
    }

    @Override
    public @NotNull List<@NotNull Examscore> selectAllExamScore(int examid) {
        Connection conn= SQLManager.getConnection();
        List<Examscore> Examscore= new ArrayList<Examscore>();
        ResultSet result=null;
        PreparedStatement pst = null;
        try {
            String sql = "select * from examscore where ExamID= ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,examid);
            result = pst.executeQuery();
            while(result.next()){
                Examscore examscore= new Examscore();
                examscore.setUid(result.getInt("uid"));
                examscore.setExamid(result.getInt("examid"));
                examscore.setConfirm(result.getInt("confirm"));
                examscore.setExamgrade(result.getInt("examgrade"));
                examscore.setCompetition(result.getString("competition"));
                Examscore.add(examscore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return Examscore;
    }
}
