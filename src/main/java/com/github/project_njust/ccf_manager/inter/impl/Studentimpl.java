package com.github.project_njust.ccf_manager.inter.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.model.Student;
import com.github.project_njust.ccf_manager.inter.Studentinter;
import com.github.project_njust.ccf_manager.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Studentimpl implements Studentinter {

    @Override
    public int insertStudent(Student record) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try{
            String sql = "insert into student(StudentID,UID,IdentityCrad) value(?,?,?)";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, record.getStudentid());
            pst.setInt(2, record.getUid());
            pst.setString(3, record.getIdentitycard());
            int rows = pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return 0;
    }

    @Override
    public int updateStudent(Student record, String StudentID) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "update student set IdentityCrad=? where StudentID=?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1,record.getStudentid());
            pst.setString(2, record.getIdentitycard());
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return 0;
    }

    @Override
    public int deleteStudent(String StudentID) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "delete from student where StudentID = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1,StudentID);
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Student selectStudentbyStudentID(String StudentID) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "select * from student where StudentID = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, StudentID);
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Student> selectStuent() {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "select * from student";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}