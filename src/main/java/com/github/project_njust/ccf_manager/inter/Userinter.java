package com.github.project_njust.ccf_manager.inter;

import com.github.project_njust.ccf_manager.model.User;

import java.util.List;

public interface Userinter {
    public int insertUser(User user);

    public int updateUser (User user,int uid);

    public int deleteUser(int uid);

    public User selectUserById(int uid);

    public List<User> selectAllUser();

}
