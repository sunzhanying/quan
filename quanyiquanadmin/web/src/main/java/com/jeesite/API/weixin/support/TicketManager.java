package com.jeesite.API.weixin.support;


import com.jeesite.API.util.HttpUtils;
import com.jeesite.API.weixin.api.TicketAPI;
import com.jeesite.API.weixin.bean.ticket.Ticket;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * TicketManager ticket 自动刷新
 *
 * @author LiYi
 */
public class TicketManager {
    private static ScheduledExecutorService scheduledExecutorService;

    private static Map<String, String> ticketMap = new LinkedHashMap<String, String>();

    private static Map<String, ScheduledFuture<?>> futureMap = new HashMap<String, ScheduledFuture<?>>();

    private static int poolSize = 2;

    private static boolean daemon = Boolean.TRUE;

    /**
     * 初始化 scheduledExecutorService
     */
    private static void initScheduledExecutorService() {
        System.out.println("开始线程.........");
        scheduledExecutorService = Executors.newScheduledThreadPool(poolSize, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable arg0) {
                Thread thread = Executors.defaultThreadFactory().newThread(arg0);
                //设置守护线程
                thread.setDaemon(daemon);
                return thread;
            }
        });
    }

    /**
     * 设置线程池
     *
     * @param poolSize
     */
    public static void setPoolSize(int poolSize) {
        TicketManager.poolSize = poolSize;
    }

    /**
     * 设置线程方式
     *
     * @param daemon
     */
    public static void setDaemon(boolean daemon) {
        TicketManager.daemon = daemon;
    }

    /**
     * 初始化ticket 刷新，每118分钟刷新一次。
     * 依赖TokenManager
     *
     * @param appid
     */
    public static void init(final String appid) {
        System.out.println("开始线程2544.........");
        if (scheduledExecutorService == null) {
            initScheduledExecutorService();
        }
        if (futureMap.containsKey(appid)) {
            return;
        }
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                String access_token = TokenManager.getToken(appid);
                Ticket ticket = TicketAPI.ticketGetticket(access_token);



                ticketMap.put(appid, ticket.getTicket());
            }
        }, 15, 260, TimeUnit.SECONDS); // 118分钟刷新一次
        futureMap.put(appid, scheduledFuture);
        System.out.println("开始线程3545.........");
    }

    /**
     * 取消 ticket 刷新
     */
    public static void destroyed() {
        scheduledExecutorService.shutdownNow();
    }

    /**
     * 获取 jsapi ticket
     *
     * @param appid
     * @return
     */
    public static String getTicket(final String appid) {
        return ticketMap.get(appid);
    }

    /**
     * 设置 jsapi ticket
     *
     * @param appid
     * @return
     */
    public static void setTicket(String appid, String ticket) {
        ticketMap.put(appid, ticket);
    }

    /**
     * 获取第一个appid 的  jsapi ticket
     * 适用于单一微信号
     *
     * @return
     */
    public static String getDefaultTicket(String mp) {
        if (mp == null) {

            System.out.print("ticketMap"+ticketMap);
            Object[] objs = ticketMap.values().toArray();
            System.out.println("输出数据："+objs);
            return objs.length > 0 ? objs[0].toString() : null;
        } else if (mp.equals("ypz")) {
            HttpUriRequest httpUriRequest = RequestBuilder.post()
                    .setUri("http://test.shixing.me:10001/api/weixin/ticket")
                    .build();

            return HttpUtils.httpRequest(httpUriRequest);
        } else if (mp.equals("cak")) {
            HttpUriRequest httpUriRequest = RequestBuilder.post()
                    .setUri("http://wx.changaikang.com:10001/api/weixin/ticket")
                    .build();

            return HttpUtils.httpRequest(httpUriRequest);
        }

        return null;
    }

}
