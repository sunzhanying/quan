
package com.jeesite.API.util;

import com.jeesite.API.weixin.api.MessageAPI;
import com.jeesite.API.weixin.bean.component.MpTemplateMessage;
import com.jeesite.API.weixin.bean.component.MpTemplateMessageItem;
import com.jeesite.API.weixin.bean.message.templatemessage.TemplateMessageResult;
import com.jeesite.API.weixin.support.TokenManager;
import com.jeesite.modules.bright.formid.dao.FormIdDao;
import com.jeesite.modules.bright.formid.entity.FormId;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class PushUtil {

    @Value("${weixin.appid}")
    private String wxAppId;
    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private FormIdDao formIdDao;
    //支付成功通知
    public static final String PUSH_PAY = "6nRPjH6vPeaO30B8VGyDTUhXzk05YQyNN6A9v2ZItlw";
    //发货
    /**
     *  物品名称{{keyword1.DATA}}
        快递单号{{keyword2.DATA}}
        快递公司{{keyword3.DATA}}
        发货时间{{keyword4.DATA}}
        购买时间{{keyword5.DATA}}
     */
    public static final String PUSH_DELIVER = "6nRPjH6vPeaO30B8VGyDTUhXzk05YQyNN6A9v2ZItlw";



    public TemplateMessageResult push(String openId, String flag, List<String> list, String consumeId) {
        MpTemplateMessage mpTemplateMessage = new MpTemplateMessage();
        String Template_id = null;
        Template_id = flag;
        mpTemplateMessage.setTemplate_id(Template_id);
        mpTemplateMessage.setTouser(openId);

        List<FormId> form_ids = formIdDao.selectByOpenId(openId);
        //formid为空
        if (form_ids.size() == 0) {
            return null;
        }
        FormId formId = form_ids.get(0);
        mpTemplateMessage.setForm_id(formId.getFormId());

        LinkedHashMap<String, MpTemplateMessageItem> map = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            MpTemplateMessageItem mpTemplateMessageItem = new MpTemplateMessageItem();
            mpTemplateMessageItem.setValue(list.get(i));
            map.put("keyword" + (i + 1), mpTemplateMessageItem);
        }
        mpTemplateMessage.setData(map);
        //如果是支付时跳转到详情页面
        if (flag.equals(PUSH_PAY) || flag.equals(PUSH_DELIVER)) {
            mpTemplateMessage.setPage("pages/mine/orderDetail/orderDetail?id="+consumeId+"&type=1");
        } else {
            mpTemplateMessage.setPage("pages/index/index");
        }
        TemplateMessageResult templateMessageResult;
        for (FormId f : form_ids) {
            mpTemplateMessage.setForm_id(f.getFormId());
            templateMessageResult = MessageAPI.messageTemplateSend(TokenManager.getToken(wxAppId), mpTemplateMessage);
            log.info("getErrcode==" + templateMessageResult.getErrcode());

            //使用过的直接删除
            if ("41029".equals(templateMessageResult.getErrcode())||
                    "41028".equals(templateMessageResult.getErrcode())) {
                Integer str = formIdDao.phyDelete(new FormId(f.getId()));
                log.info("删除formId=" + formId.getId() + "===" + str);
            }

            if ("0".equals(templateMessageResult.getErrcode())) {
                    int t =f.getTimes();
                    t--;
                    if (t <= 0) {
                        Integer str = formIdDao.phyDelete(new FormId(f.getId()));
                        log.info("删除formId=" + formId.getId() + "===" + str);
                    } else {
                        f.setTimes(t);
                        f.preUpdate();
                        formIdDao.update(f);
                    }
                return templateMessageResult;
            }
        }


        return null;
    }



}
