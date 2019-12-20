package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChangePassword extends Service {
    public ChangePassword() {
        super("changepassword", UserType.PRINCIPAL,UserType.ADMIN,UserType.STUDENT);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String password1 = input.getData().getString("oldpassword");
        String password2 = input.getData().getString("newpassword");
        User us = input.getUser();
        int uid = us.getUid();

        if(password1.equals(password2)){
            IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason","修改密码与当前密码一致");
            return res ;
        }
        User user = SQLManager.getUserManager().selectUserById(uid);
        if(!password1.equals(user.getPassword())){
            IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason","密码输入错误");
            return res ; 
        }
        user.setPassword(password2);
        SQLManager.getUserManager().updateUser(user);

        IResponse res =IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res ;
    }
}
