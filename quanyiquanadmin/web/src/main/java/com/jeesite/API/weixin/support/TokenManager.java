package com.jeesite.API.weixin.support;


import com.jeesite.API.weixin.api.TokenAPI;
import com.jeesite.API.util.HttpUtils;
import com.jeesite.API.weixin.bean.token.Token;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * TokenManager token 自动刷新
 *
 * @author LiYi
 */
public class TokenManager {

    private static ScheduledExecutorService scheduledExecutorService;

    private static Map<String, String> tokenMap = new LinkedHashMap<String, String>();

    private static Map<String, ScheduledFuture<?>> futureMap = new HashMap<String, ScheduledFuture<?>>();

    private static int poolSize = 2;

    private static boolean daemon = Boolean.TRUE;

    /**
     * 初始化 scheduledExecutorService
     */
    private static void initScheduledExecutorService() {
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
        TokenManager.poolSize = poolSize;
    }

    /**
     * 设置线程方式
     *
     * @param daemon
     */
    public static void setDaemon(boolean daemon) {
        TokenManager.daemon = daemon;
    }

    /**
     * 初始化token 刷新，每118分钟刷新一次。
     *
     * @param appid
     * @param secret
     */
    public static void init(final String appid, final String secret) {
        if (scheduledExecutorService == null) {
            initScheduledExecutorService();
        }
        if (futureMap.containsKey(appid)) {
            futureMap.get(appid).cancel(true);
        }
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Token token = TokenAPI.token(appid, secret);


                System.out.println("token+lvshukai测试"+token);
                System.out.println("getAccess_token+lvshukai cuo_wu_xin_xing"+token.getAccess_token());

                System.out.println("getExpires_in+lvshukai cuo_wu_xin_xing"+token.getExpires_in());

                tokenMap.put(appid, token.getAccess_token());


            }
        }, 0, 260, TimeUnit.SECONDS); // 118分钟刷新一次 7080暂时改为3000
        futureMap.put(appid, scheduledFuture);
    }

    /**
     * 取消 token 刷新
     */
    public static void destroyed() {
        scheduledExecutorService.shutdownNow();
    }

    /**
     * 获取 access_token
     *
     * @param appid
     * @return
     */
    public static String getToken(String appid) {
        return tokenMap.get(appid);
    }

    /**
     * 设置 access_token
     *
     * @param appid
     * @return
     */
    public static void setToken(String appid, String token) {
        tokenMap.put(appid, token);
    }

    /**
     * 获取第一个appid 的 access_token
     * 适用于单一微信号
     *
     * @return
     */
    public static String getDefaultToken(String mp) {
        if (mp == null) {
            Object[] objs = tokenMap.values().toArray();
            return objs.length > 0 ? objs[0].toString() : null;
        } else if (mp.equals("ypz")) {
            HttpUriRequest httpUriRequest = RequestBuilder.post()
                    .setUri("http://test.shixing.me:10001/api/weixin/token")
                    .build();

            return HttpUtils.httpRequest(httpUriRequest);
        } else if (mp.equals("cak")) {
            HttpUriRequest httpUriRequest = RequestBuilder.post()
                    .setUri("http://wx.changaikang.com:10001/api/weixin/token")
                    .build();

            return HttpUtils.httpRequest(httpUriRequest);
        }

        return null;
    }

}
