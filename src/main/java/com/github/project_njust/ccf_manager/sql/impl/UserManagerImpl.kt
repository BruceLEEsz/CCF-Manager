package com.github.project_njust.ccf_manager.sql.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.model.Student;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.sql.IUserManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.naming.Name;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManagerimpl implements IUserManager {
    @Override
    public @NotNull User createUser(@NotNull String studentid) {

        return null;
    }

    @Override
    public int updateUser(@NotNull User user, int uid) {
        Connection conn= SQLManager.getConnection();
        int result=0;
        PreparedStatement pst = null;
        try {
            String sql = "update user set Password=?,Name=?,TYPE =?,Data=? where UID=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,user.getPassword());
            pst.setString(2,user.getName());
            pst.setInt(3,user.getType());
//TODO data
            pst.setInt(4,user.getUid());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return result;
    }

    @Override
    public int deleteUser(int uid) {
        Connection conn= SQLManager.getConnection();
        int result=0;
        PreparedStatement pst = null;
        try {
            String sql = "delete from user where UID = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,uid);
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return result;
    }

    @Override
    public @Nullable User selectUserById(int uid) {
        Connection conn= SQLManager.getConnection();
        ResultSet result=null;
        PreparedStatement pst = null;
        try {
            String sql = "select * from user where UID=?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,uid);
            result = pst.executeQuery();
            if(result.next()){
                return new User(result.getInt("uid"), result.getString("name"),result.getInt("Type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return null;
    }

    @Override
    public @Nullable User selectUserByName(@NotNull String name) {
        Connection conn= SQLManager.getConnection();
        ResultSet result=null;
        PreparedStatement pst = null;
        try {
            String sql = "select * from user where Name=?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, name);
            result = pst.executeQuery();
            if(result.next()){
                return new User(result.getInt("uid"), result.getString("name"),result.getInt("Type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return null;
    }

    @Override
    public @NotNull List<@NotNull User> selectAllUser() {
        Connection conn= SQLManager.getConnection();
        List<User> User= new ArrayList<User>();
        ResultSet result=null;
        PreparedStatement pst = null;
        try {
            String sql = "select * from user";
            pst = conn.prepareStatement(sql);
            result= pst.executeQuery();
            while(result.next()){
                User user=new User();
                user.setUid(result.getInt("uid"));
                user.setName(result.getString("name"));
                user.setType(result.getInt("type"));
                User.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLManager.evictConnection(conn);
        return User;
    }
}
