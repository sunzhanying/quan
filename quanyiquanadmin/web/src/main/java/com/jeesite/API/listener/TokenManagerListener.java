package com.jeesite.API.listener;


import com.jeesite.common.config.Global;
import com.jeesite.API.weixin.support.TokenManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/*
@WebListener
*/
public class TokenManagerListener implements ServletContextListener {


    //卖方
    private String wxAppId = Global.getConfig("weixin.appid");
    private String wxAppSecret = Global.getConfig("weixin.appsecret");
    //买方
    private String wxAppIdA = Global.getConfig("weixin.appidA");
    private String wxAppSecretA = Global.getConfig("weixin.appsecretA");

    @Value("${weixin.mp:#{null}}")
    private String mp;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("初始化TokenManager");

        //卖方小程序
        TokenManager.init(wxAppId, wxAppSecret);
        //买方小程序
        TokenManager.init(wxAppIdA, wxAppSecretA);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        // WEB容器  关闭时调用
        TokenManager.destroyed();
    }
}
