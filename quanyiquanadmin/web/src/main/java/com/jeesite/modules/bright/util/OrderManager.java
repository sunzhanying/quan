package com.jeesite.modules.bright.util;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.order.entity.Order;
import com.jeesite.modules.order.service.ExpireService;
import com.jeesite.modules.order.service.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 订单未支付处理
 */
@Component
public class OrderManager {

    private final Log log = LogFactory.getLog(getClass());

    @Value("${order_qx_time}")
    private String orderQxTime;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ExpireService expireService;

    private static Map<String,Object> ordermap = new LinkedHashMap<>();

    /**
     * 初始化订单 刷新，每15秒刷新一次。
     *
     */
    public  void init() {
        //查询未支付订单
        Order order = new Order();
        order.setZt(Order.PAY_STATUS_DZF);
        List<Order> orderList = orderService.findList(order);
        for (Order order1:orderList){
            ordermap.put(order1.getId(), order1);
        }
        //创建一个定长线程池，支持定时及周期性任务执行——定期执行
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        //延迟5秒后每15秒执行一次
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            public void run() {
                //处理卖家卡券过期失效，
                expireCard();
                //循环map
                Iterator<Map.Entry<String,Object>> it = ordermap.entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry<String, Object> entry= it.next();
                    String key= entry.getKey();
                    Order order = (Order) entry.getValue();
                    if (DateUtils.pastMinutes(order.getCreateDate()) > Integer.parseInt(orderQxTime)){
                        log.info("取消订单>>>>>>>"+ order.getId());
                        orderService.qxOrderLjSpKc(true, order);
                        it.remove();
                    }
                }
            }
        }, 5, 15, TimeUnit.SECONDS);
    }

    private void expireCard() {
        //在凌晨00点到00:02:00才去执行
        SimpleDateFormat sf = new SimpleDateFormat("HHmmss");
        String s = sf.format(new Date());
        log.info("当前时间s:" + s);
        if(s.compareTo("000200") < 0){//当前时间小于凌晨2分钟,字符串000200
            log.info("进入方法修改卡券失效方法。");
            //查询表a_qyhs_mx，状态是3，出售中的卡券，如果失效时间已过，则设置成2、审核失败
            expireService.expireCard();
        }
    }

    /**
     * 获取订单
     * @param id
     * @return
     */
    public static Object getOrder(String id) {
        return ordermap.get(id);
    }

    /**
     * 增加订单
     * @param order
     * @return
     */
    public static void addOrder(Order order) {
        ordermap.put(order.getId(), order);
    }

    /**
     * 删除订单
     * @param key
     * @return
     */
    public static void removeOrder(String key) {
        Iterator<String> iter = ordermap.keySet().iterator();
        while (iter.hasNext()){
            String key1 = iter.next();
            if (key.equals(key1)){
                iter.remove();
            }
        }
    }
}
