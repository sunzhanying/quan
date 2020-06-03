package com.jeesite.API.weixin.bean.message.templatemessage;

import com.jeesite.API.weixin.bean.BaseResult;

public class TemplateMessageResult extends BaseResult {

    private Long msgid;

    public Long getMsgid() {
        return msgid;
    }

    public void setMsgid(Long msgid) {
        this.msgid = msgid;
    }


}
