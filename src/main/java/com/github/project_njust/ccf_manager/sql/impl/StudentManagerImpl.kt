package com.github.project_njust.ccf_manager.sql.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.model.Examscore;
import com.github.project_njust.ccf_manager.model.Student;
import com.github.project_njust.ccf_manager.sql.IStudentManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentManagerimpl implements IStudentManager {
    @Override
    public int insertStudent(@NotNull Student record) {
        Connection conn= SQLManager.getConnection();
        int result = 0;
        PreparedStatement pst = null;
        try {
            String sql = "insert into student(IdentityCrad, Data)  value(?, '{}')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1,record.getIdentitycard());
//TODO data
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return result;
    }

    @Override
    public int updateStudent(@NotNull Student record, String StudentID) {
        Connection conn= SQLManager.getConnection();
        int result=0;
        PreparedStatement pst = null;
        try {
            String sql = "update student set IdentityCrad=?,Data=? where StudentID=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,record.getIdentitycard());
//TODO data
            pst.setString(3,record.getStudentId());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return result;
    }

    @Override
    public int deleteStudent(@NotNull String StudentID) {
        Connection conn= SQLManager.getConnection();
        int result=0;
        PreparedStatement pst = null;
        try {
            String sql = "delete from student where StudentID = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,StudentID);
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return result;
    }

    @Override
    public @Nullable Student selectStudentByStudentID(@NotNull String StudentID) {
        Connection conn= SQLManager.getConnection();
        ResultSet result=null;
        PreparedStatement pst = null;
        try {
            String sql = "select * from student where StudentID=?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1,StudentID);
            result = pst.executeQuery();
            if(result.next()){
                return new Student(result.getString("StudentID"), result.getInt("uid"),result.getString("IdentityCard"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return null;
    }

    @Override
    public @NotNull List<@NotNull Student> selectStudents() {
        Connection conn= SQLManager.getConnection();
        List<Student> Student= new ArrayList<Student>();
        ResultSet result=null;
        PreparedStatement pst = null;
        try {
            String sql = "select * from student";
            pst = conn.prepareStatement(sql);
            result= pst.executeQuery();
            while(result.next()){
                Student student=new Student();
                student.setStudentId(result.getString("StudentId"));
                student.setUid(result.getInt("uid"));
                student.setIdentitycard(result.getString("IdentityCard"));
                Student.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return Student;
    }

    @Override
    public @Nullable Student selectStudent(int uid) {
        Connection conn = SQLManager.getConnection();
        ResultSet result = null;
        PreparedStatement pst = null;
        try {
            String sql = "select * from student where UID=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, uid);
            result = pst.executeQuery();
            if (result.next()) {
                return new Student(result.getString("StudentID"), result.getInt("uid"), result.getString("IdentityCard"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return null;
    }
}
