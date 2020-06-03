package com.jeesite.modules.bright.util;

import com.jeesite.modules.bright.t.entity.khxw.KhXw;
import com.jeesite.modules.bright.t.service.khxw.KhXwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class KhXwUtil {

    @Autowired
    private KhXwService khXwService;

    public static KhXwUtil khXwUtil;

    @PostConstruct
    public void init(){
        khXwUtil = this;
    }

    public static void addXw(String id,String khid,String comet,String orderId){
        KhXw khXw = new KhXw();
        khXw.setKhid(khid);
        khXw.setXwms(comet);
        khXw.setXwmc(id);
        khXw.setDiid(orderId);
        khXwUtil.khXwService.save(khXw);
    }
}
