package com.jeesite.API.util.ZyUtil;

import com.jeesite.modules.bright.khvipcard.entity.KhVipcard;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;

import java.util.UUID;
import java.util.concurrent.*;

/*  扫码定时服务
 * @Author 马晓亮
 * @Date $ $
 * @Param $
 * @return $
 **/
public class SweepCodeService {

    static ConcurrentHashMap<String, Future> futureMap = new ConcurrentHashMap<String, Future>();
    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

    /**
     * 定时轮询扫码器
     */
    public static void setScheduler(String khid, KhVipcard khVipcard){
        System.out.println("进入线程用户："+ khid);
        String uuid = UUID.randomUUID().toString();
        Future future = scheduler.scheduleAtFixedRate(new SweepCode(uuid, khid, khVipcard, futureMap), 6, 4, TimeUnit.SECONDS);
        futureMap.put(uuid, future);
        //futureMap.remove(id);
        //scheduler.shutdown();
    }

}
