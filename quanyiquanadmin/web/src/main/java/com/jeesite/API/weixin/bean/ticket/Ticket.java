package com.jeesite.API.weixin.bean.ticket;

import com.jeesite.API.weixin.bean.BaseResult;

public class Ticket extends BaseResult {

    private String ticket;

    private Integer expires_in;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }


}
