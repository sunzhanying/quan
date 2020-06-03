package com.jeesite.API.listener;


import com.jeesite.modules.bright.util.CtydManager;
import com.jeesite.modules.bright.util.OrderManager;
import com.jeesite.modules.bright.util.OrderShManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class OrderManagerListener implements ServletContextListener {
    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private OrderManager orderManager;
    @Autowired
    private OrderShManager orderShManager;

    @Value("${weixin.mp:#{null}}")
    private String mp;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // WEB容器 初始化时调用
        if (mp == null) {
            log.info("订单待支付订单刷新...");
            orderManager.init();
            log.info("自动提现刷新...");
            orderShManager.init();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Destroy OrderManager...");
        // WEB容器  关闭时调用
       // OrderManager.destroyed();
    }
}
