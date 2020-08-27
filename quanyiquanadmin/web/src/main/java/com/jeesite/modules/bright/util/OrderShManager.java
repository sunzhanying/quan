package com.jeesite.modules.bright.util;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.order.service.OrderService;
import com.jeesite.modules.txsh.entity.Txsh;
import com.jeesite.modules.txsh.service.TxshService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 自动提现定时器
 */
@Component
public class OrderShManager {

    @Autowired
    private TxshService txshService;

    /**
     * 初始化订单 刷新，每15秒刷新一次。
     *
     */
    public  void init() {

        //创建一个定长线程池，支持定时及周期性任务执行——定期执行
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        //延迟0分后每10分执行一次
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            public void run() {
                //查询未提现或提现失败的数据
                Txsh txsh = new Txsh();
                /*txsh.getSqlMap().getWhere().and("create_date", QueryType.GTE, DateUtils.getDate("yyyy-MM-dd HH:mm:ss", -24, Calendar.HOUR));
                txsh.getSqlMap().getWhere().andBracket("zt", QueryType.EQ, Txsh.TX_STATUS_SQZ, 1)
                        .or("zt", QueryType.EQ, Txsh.TX_STATUS_SB, 2).endBracket();*/
                //往前推24小时之后
                txsh.getSqlMap().getWhere().and("create_date", QueryType.GTE, DateUtils.getDate("yyyy-MM-dd HH:mm:ss", -24, Calendar.HOUR));
                //往前推12小时之前
                txsh.getSqlMap().getWhere().and("create_date", QueryType.LT, DateUtils.getDate("yyyy-MM-dd HH:mm:ss", -12, Calendar.HOUR));
                //审核中的
                txsh.getSqlMap().getWhere().and("zt", QueryType.EQ, Txsh.TX_STATUS_SQZ);
                List<Txsh> txshList = txshService.findList(txsh);
                //自动提现，切记 不要打开，不然直接给用户打钱了；过去12个小时到24小时待审核的订单自动打款
                txshList.forEach(item ->{
                    //txshService.txsh(item); todo 暂时不提现，提现失败的不应该再提现，现在已经可以手动设置 3提现审核不通过
                });
            }
        }, 0, 10, TimeUnit.MINUTES);
    }

}
