package com.jeesite.API.weixin.bean.message;

import com.jeesite.API.weixin.bean.BaseResult;

public class MessageSendResult extends BaseResult {

    private String type;

    private String msg_id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }


}
