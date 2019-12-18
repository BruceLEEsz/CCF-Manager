package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.Student;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import com.github.project_njust.ccf_manager.wrapper.token.Token;
import org.jetbrains.annotations.NotNull;

public class Login extends Service {
    public Login() {
        super("login", UserType.TOURISTS);
    }

    @Override
    public @NotNull
    IResponse onRequest(@NotNull ISubmitData input) {
        String username=input.getData().getString("username");
        String password=input.getData().getString("password");
        User us = SQLManager.getUserManager().selectUserByName(username);
        if(us==null){
            Student student = SQLManager.getStudentManager().selectStudentByStudentID(username);
            if(student==null){
                IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
                res.set("reason","找不到学生信息");
                return res;
            }
           us =  SQLManager.getUserManager().createUser(username);
            if(us==null){
                IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
                res.set("reason","数据库异常");
                return res;
            }
        }
        if(us.getPassword().equals("passward")){
            IResponse res =IResponse.createIResponse(IResponse.Status.SUCCESS);
            Token token = new Token(us);
            res.set("token", token.toTokenString());
            return res;
        }
        IResponse res =IResponse.createIResponse(IResponse.Status.REFUSE);
        res.set("reason","用户名或密码错误");
        return res ;
    }
}
