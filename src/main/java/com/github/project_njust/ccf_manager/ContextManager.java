package com.github.project_njust.ccf_manager;

import com.github.project_njust.ccf_manager.servlet.DataServlet;
import com.github.project_njust.ccf_manager.servlet.FileUploadServlet;
import com.github.project_njust.ccf_manager.sql.IUserManager;
import com.github.project_njust.ccf_manager.wrapper.token.TokenManager;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextManager implements ServletContextListener {
    private static ServletContext servletContext;


    public static ServletContext getServletContext() {//
        return servletContext;
    }

    public void contextInitialized(ServletContextEvent evt) {
        servletContext = evt.getServletContext();
        FileManager.checkFolder();
        SQLManager.init();
        TokenManager.init();
        FileUploadServlet.Companion.init();
        DataServlet.Companion.init();
        if (SQLManager.getUserManager().getUID("admin") < 0) {
            SQLManager.getUserManager().createAdmin("admin", IUserManager.hashPassword("admin"));
            SQLManager.getUserManager().createPrincipal("社团", IUserManager.hashPassword("123456"));
        }
        Logger.getLogger(ContextManager.class).info("CCF Manager初始化完成");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        FileManager.clearTempFiles();
    }

    @NotNull
    public static String getEncoding() {
        return servletContext.getInitParameter("encoding");
    }
}
