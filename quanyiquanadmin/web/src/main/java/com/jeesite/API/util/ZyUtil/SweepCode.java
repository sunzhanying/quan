package com.jeesite.API.util.ZyUtil;

/*扫码定时业务类
 * @Author 马晓亮
 * @Date $ $
 * @Param $
 * @return $
 **/
import com.jeesite.API.util.ApplicationContextProvider;
import com.jeesite.API.zyapi.BaseResult;
import com.jeesite.API.zyapi.ZyAPI;
import com.jeesite.modules.bright.hykjl.entity.VipcardJl;
import com.jeesite.modules.bright.hykjl.service.VipcardJlService;
import com.jeesite.modules.bright.khvipcard.entity.KhVipcard;
import com.jeesite.modules.bright.khvipcard.service.KhVipcardService;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;


public class SweepCode implements Runnable{


    private ZyAPI zyAPI = ApplicationContextProvider.getBean(ZyAPI.class);

    private KhVipcardService khVipcardService = ApplicationContextProvider.getBean(KhVipcardService.class);;

    private VipcardJlService vipcardJlService = ApplicationContextProvider.getBean(VipcardJlService.class);;



    private ConcurrentHashMap<String, Future> futureMap;
    private int count = 0;
    private String uuid;
    private String khId;
    private KhVipcard khVipcard;

    public SweepCode (String uuid, String khid, KhVipcard khVipcard, ConcurrentHashMap<String, Future> futureMap){
        this.khId = khid;
        this.uuid = uuid;
        this.khVipcard = khVipcard;
        this.futureMap = futureMap;
    }

    @Override
    public void run() {
        count++;
        System.out.println("开始轮训" + uuid +"   this is " + count);

        try {
            //查询第三方会员余额
            BaseResult baseResult = zyAPI.queryByNumber(khId);
            //当前会员卡余额于第三方是否相等
            if ("success".equals(baseResult.getStatus())){
                if (baseResult.getData().getBalance() < khVipcard.getYe()){
                    //增加线下消费记录
                    System.out.print("金额发生改变，第三方余额"+baseResult.getData().getBalance() + "卡余额"+khVipcard.getYe());
                    BigDecimal b1 = new BigDecimal(Double.toString(khVipcard.getYe()));
                    BigDecimal b2 = new BigDecimal(Double.toString(baseResult.getData().getBalance()));
                    VipcardJl vipcardJl = new VipcardJl();
                    vipcardJl.setVipCardId(khVipcard.getId());
                    vipcardJl.setKhid(khId);
                    vipcardJl.setDkje(b1.subtract(b2).doubleValue());
                    vipcardJl.setYe(baseResult.getData().getBalance());
                    vipcardJl.setKcfs(VipcardJl.VIP_CARD_KCFS_XX);
                    //添加会员卡使用记录
                    vipcardJlService.save(vipcardJl);
                    //更新会员卡余额
                    khVipcard.setYe(baseResult.getData().getBalance());
                    khVipcardService.update(khVipcard);
                }
            }

            //第30秒关闭定时器任务
            if (count > 9 || baseResult.getData().getBalance() < khVipcard.getYe() || !"success".equals(baseResult.getStatus())) {
                Future future = futureMap.remove(uuid);
                future.cancel(true);
                System.out.println("uuid "+uuid +"had cancel");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public ConcurrentHashMap<String, Future> getFutureMap() {
        return futureMap;
    }

    public void setFutureMap(ConcurrentHashMap<String, Future> futureMap) {
        this.futureMap = futureMap;
    }

    public String getKhId() {
        return khId;
    }

    public void setKhId(String khId) {
        this.khId = khId;
    }
}
