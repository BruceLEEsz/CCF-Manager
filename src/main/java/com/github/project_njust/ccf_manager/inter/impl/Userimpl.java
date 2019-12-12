package com.github.project_njust.ccf_manager.inter.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.inter.Userinter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Userimpl implements Userinter {
    @Override
    public int insertUser(User user) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try{
            String sql = "insert into user(Password,Name,Type) value(?,?,?)";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, user.getPassword());
            pst.setString(2, user.getName());
            pst.setInt(3, user.getType());
            int rows = pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return 0;
    }

    @Override
    public int updateUser(User user, int uid) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "update user set Name=?,Password=? where UID=?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1,user.getName());
            pst.setString(2,user.getPassword());
            pst.setInt(3, user.getUid());
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return 0;
    }

    @Override
    public int deleteUser(int uid) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "delete from user where UID = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, uid);
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public User selectUserById(int uid) {
        Connection conn= SQLManager.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "select * from user where UID = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, uid);
            int rows = pst.executeUpdate();
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
            String sql = "select * from user";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
