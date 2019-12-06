package com.github.project_njust.ccf_manager;

import org.apache.log4j.Logger;

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
    }



    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
