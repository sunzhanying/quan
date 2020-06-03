package com.jeesite.modules.bright.util;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.bright.ctyd.entity.Ctyd;
import com.jeesite.modules.bright.ctyd.service.CtydService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 餐厅预订超时取消
 */
@Component
public class CtydManager {

    @Autowired
    private CtydService ctydService;


    @Value("${order_sh_time}")
    private String orderShTime;

    /**
     * 初始化订单 刷新，每15秒刷新一次。
     *
     */
    public  void init() {

        //创建一个定长线程池，支持定时及周期性任务执行——定期执行
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        //延迟1秒后每3秒执行一次
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Ctyd ctyd = new Ctyd();
                ctyd.setZt(Ctyd.CTYD_ZT_YDZ);
                //查询未支付订单
                List<Ctyd> ctydList = ctydService.findList(ctyd);
                for (Ctyd ctyd1:ctydList){
                    //大于7天自动收货
                    if (DateUtils.pastMinutes(ctyd1.getCreateDate()) > 30){
                        ctydService.updateZt(ctyd1.getId(), Ctyd.CTYD_ZT_YQX);
                    }
                }
            }
        }, 5, 60, TimeUnit.SECONDS);
    }

}
