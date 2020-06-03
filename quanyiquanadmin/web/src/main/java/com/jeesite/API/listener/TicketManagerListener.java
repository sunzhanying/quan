package com.jeesite.API.listener;


import com.jeesite.common.config.Global;
import com.jeesite.API.weixin.support.TicketManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import javax.servlet.annotation.WebListener;

/*
@WebListener
*/

public class TicketManagerListener implements ServletContextListener {
    private final Log log = LogFactory.getLog(getClass());


    private String wxAppId= Global.getConfig("weixin.appid");

    @Value("${weixin.mp:#{null}}")
    private String mp;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // WEB容器 初始化时调用
        // TicketManager 依赖 TokenManager，确保TokenManager.init 先被调用
        System.out.println("mp======================"+mp);
        System.out.println("wxAppId======================"+wxAppId);
        if (mp == null) {
            log.info("Init TicketManager...");
            TicketManager.init(wxAppId);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Destroy TicketManager...");
        // WEB容器  关闭时调用
        TicketManager.destroyed();
    }
}
