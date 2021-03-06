package com.jeesite.modules.bright.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/*
    定时关闭引擎
 */
public class StopEngineUtil {

    public  static Timer p_offtimer; //引擎

    public  static  int engineStatus = 0; //引擎状态 0 关闭 1 开启

    /*
        关闭引擎
     */
    public static  void  offEngine(){
        p_offtimer.cancel();
        engineStatus = 0;
    }


    /*
        开启引擎
     */
    public  static  void  onEngine(){

            System.out.print("开启引擎********************");

            p_offtimer = new Timer();
            engineStatus = 1;

            //执行任务
            auto_offEngine();

    }


    /*
       自动关闭引擎
    */
    public  static  void  auto_offEngine(){

        /*Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);//每次启动都是明天的凌晨0点为开始执行时间(一定是明天的时间点，如果是今天的零点，今天的零点已经过去了，就不准了，他就以当前时间点为开始时间点，所以要以一个未到的时间点为开始时间)
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),hour,0,0);*/
        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
        //定制每天的20:00:00执行
        //自动关闭时间
        String stop =  PushEngineUtil.getPro("pushEngine.autoStop");
        String[] str = stop.split(":");
        if (str.length >= 2){
            String s = str[0];
            String first = s.substring(0,1);
            String tim = "20";
            if ("0".equals(first)){
                tim = s.substring(1,2);
            }else {
                tim = s;
            }
            calendar.set(year, month, day, Integer.parseInt(tim), 00, 00);
        }else {
            calendar.set(year, month, day, 8, 00, 00);
        }
        Date date = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //TODO 需要做的事情 每天6点关闭引擎
                //
                TimerEngineUtil.offEngine("1");


            }
        },date);
    }



}
