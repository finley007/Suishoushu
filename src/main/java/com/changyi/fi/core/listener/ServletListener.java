package com.changyi.fi.core.listener;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.token.Token;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletListener implements ServletContextListener {

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        LogUtil.info(this.getClass(), "Tomcat stop...");
        saveSession();

    }

    private void saveSession() {
        LogUtil.info(this.getClass(), "Save token");
        Token.save();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        LogUtil.info(this.getClass(), "Tomcat start...");
        initSession();
    }

    private void initSession() {
        LogUtil.info(this.getClass(), "Init token");
        Token.init();
    }

}
