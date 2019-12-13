package com.github.project_njust.ccf_manager.sql;

import com.github.project_njust.ccf_manager.model.User;

import java.util.List;

public interface IUserManager {

    public User createUser(String studentid);

    public int updateUser (User user,int uid);

    public int deleteUser(int uid);

    public User selectUserById(int uid);

    public List<User> selectAllUser();

}
