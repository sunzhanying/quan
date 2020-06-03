/*
package com.thinkgem.jeesite.API.util;


import com.thinkgem.jeesite.API.entity.MpTemplateMessage;
import com.thinkgem.jeesite.API.entity.MpTemplateMessageItem;
import MessageAPI;
import TemplateMessageResult;
import TokenManager;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.formid.dao.FormIdDao;
import com.thinkgem.jeesite.modules.formid.entity.FormId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class PushUtils {

    public static final String TID_PAY="k4Nv0_ElVklD5ZxCJzeTb1K-GrnQVImpHDPjACowu2s";
    public static final String TID_INVITE="zKbQxrrY9bX-L8_exXiaUvH51tVXwM-n2JbXc8k5Lis";

    @Autowired
    private FormIdDao formIdDao;
    public TemplateMessageResult push(String openId, String flag, List<String> list, String consumeId){
        MpTemplateMessage mpTemplateMessage=new MpTemplateMessage();
        String Template_id=null;
        Template_id=flag;
        mpTemplateMessage.setTemplate_id(Template_id);
        mpTemplateMessage.setTouser(openId);
        List<FormId> form_ids=formIdDao.selectByOpenId(openId);
        //formid为空
        if(form_ids.size()==0){
            return null;
        }
        FormId formId=form_ids.get(0);
        mpTemplateMessage.setForm_id(formId.getFormId());

        LinkedHashMap<String ,MpTemplateMessageItem> map =new LinkedHashMap();
        //生成key_word
        List<String> key_word=list;
        for (int i = 0; i <key_word.size() ; i++) {
            MpTemplateMessageItem mpTemplateMessageItem=new MpTemplateMessageItem();
            mpTemplateMessageItem.setValue(key_word.get(i));
            map.put("keyword"+(i+1),mpTemplateMessageItem);
        }
        mpTemplateMessage.setData(map);


        mpTemplateMessage.setPage("pages/index/index");
        System.out.println(mpTemplateMessage.getPage());
        TemplateMessageResult templateMessageResult= MessageAPI.messageTemplateSend(TokenManager.getToken(Global.getConfig("weixin.appid")),mpTemplateMessage);
        System.out.println("getErrcode=="+templateMessageResult.getErrcode());
        while (!"0".equals(templateMessageResult.getErrcode())){
            if(!"41030".equals(templateMessageResult.getErrcode()) || !"40037".equals(templateMessageResult.getErrcode())){

                Integer str=formIdDao.del(formId.getId());
                if(str==0){
                    return null;
                }
                System.out.println("删除formId="+formId.getId()+"==="+str);
            }
            form_ids=formIdDao.selectByOpenId(openId);
            if(form_ids.size()==0){
                return null;
            }
            formId=form_ids.get(0);
            mpTemplateMessage.setForm_id(formId.getFormId());
            templateMessageResult= MessageAPI.messageTemplateSend(TokenManager.getToken(Global.getConfig("weixin.appid")),mpTemplateMessage);
            System.out.println("getErrcode=="+templateMessageResult.getErrcode());
        }
        return templateMessageResult;
    }

}
*/
