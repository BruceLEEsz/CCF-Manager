package com.github.project_njust.ccf_manager.service;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;
import com.github.project_njust.ccf_manager.wrapper.token.Token;
import com.github.project_njust.ccf_manager.wrapper.token.TokenKt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleService extends Service {

    public ExampleService() {
        super("login", UserType.TOURISTS);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        int times = input.getData().getInt("times");
        User user = input.getUser();

        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        res.set("res", Arrays.asList(new String[]{}));
        res.set("score", 100);
        return res;
//
//
//        String username = input.getData().getString("username");
//        String password = input.getData().getString("password");
//
//        User user = null;
//        String s = TokenKt.hashSHA256WithSalt(password, username, "");
//        if (s.equals(user.getPassword())) {
//            IResponse resp = IResponse.createIResponse();
//            Token token = new Token(user);
//            resp.set("token", token.toTokenString());
//            return resp;
//        } else {
//            IResponse resp = IResponse.createIResponse(IResponse.Status.REFUSE);
//            resp.set("Reason", "密码错误");
//            return resp;
//        }
    }
}
