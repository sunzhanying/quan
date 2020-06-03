package com.jeesite.modules.bright.util;


import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.bright.push.entity.push.Push;
import com.jeesite.modules.bright.push.service.push.PushService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/*
    定时引擎
 */

public class TimerEngineUtil {

    private static PushService pushService = SpringUtils.getBean(PushService.class);

    private  static Timer p_timer; //引擎

    private static long auto_timeInterval = 24 * 60 * 60 * 1000;//自动处理的间隔 一天的间隔

    public  static  int engineStatus = 0; //引擎状态 0 关闭 1 开启

    private  static  long enginePoll_timeInterval =  Integer.parseInt(PushEngineUtil.getPro("pushEngine.interval"))* 60 * 1000; //引擎轮询的间隔 默认3分钟


    private static  Date engine_startDate;//引擎开启时间

    /*
        关闭引擎
     */
    public static  void  offEngine(String mode){
        p_timer.cancel();
        engineStatus = 0;
        //修改配置
        PushEngineUtil.updatePro("pushEngine.status","0");


        System.out.print("modemodemodemode"+mode);
        //开启方式
        PushEngineUtil.updatePro("pushEngine.offMode",mode);
    }


    /*
        开启引擎
     */
    public  static  void  onEngine(){

        System.out.print("开启引擎********************");

        p_timer = new Timer();
        engineStatus = 1;
        //修改配置
        PushEngineUtil.updatePro("pushEngine.status","1");

        //开启方式
        PushEngineUtil.updatePro("pushEngine.offMode","1");


        //开启时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        engine_startDate = new Date();
        PushEngineUtil.updatePro("pushEngine.newStart",sdf.format(engine_startDate));
        //执行任务
        exe_method(engine_startDate);

    }



    /*
        自动开启引擎
     */
    public  static  void  auto_onEngine(int  hour) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);//每次启动都是明天的凌晨0点为开始执行时间(一定是明天的时间点，如果是今天的零点，今天的零点已经过去了，就不准了，他就以当前时间点为开始时间点，所以要以一个未到的时间点为开始时间)
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, 0, 0);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //TODO 需要做的事情 每天8点开启引擎
                //
                onEngine();


            }
        }, calendar.getTime(), auto_timeInterval);
    }



    /*
       自动关闭引擎
    */
    public  static  void  auto_offEngine(int  hour){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);//每次启动都是明天的凌晨0点为开始执行时间(一定是明天的时间点，如果是今天的零点，今天的零点已经过去了，就不准了，他就以当前时间点为开始时间点，所以要以一个未到的时间点为开始时间)
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),hour,0,0);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //TODO 需要做的事情 每天6点关闭引擎
                //
                offEngine("1");


            }
        },calendar.getTime(),auto_timeInterval);
    }



    /*
        引擎自动执行的任务（写到具体的业务代码里吧）
     */
    private  static void exe_method(Date date){



        p_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //TODO 需要做的事情 引擎执行方法
                Date currDate = new Date();
                // 执行推送功能
                System.out.print("测试引擎自动调用,现在执行时间是"+currDate+"\n");
                Push push = new Push();
                //4 状态为空时的判断
                push.setStatu("4");
                pushService.findList(push);
            }
        },date,Integer.parseInt(PushEngineUtil.getPro("pushEngine.interval"))* 60 * 1000);
    }


}
