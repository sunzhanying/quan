package com.jeesite.API.weixin.bean.component;


import java.util.LinkedHashMap;

public class MpTemplateMessage {

    private String touser;//是	接收者（用户）的 openid

    private String template_id;//是	所需下发的模板消息的id

    private String page;//否	点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。

    private String form_id;//是	表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id

    private LinkedHashMap<String, MpTemplateMessageItem> data; //是	模板内容，不填则下发空模板

    private String color;//否	模板内容字体的颜色，不填默认黑色 【废弃】

    private String emphasis_keyword;//否	模板需要放大的关键词，不填则默认无放大

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public LinkedHashMap<String, MpTemplateMessageItem> getData() {
        return data;
    }

    public void setData(LinkedHashMap<String, MpTemplateMessageItem> data) {
        this.data = data;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEmphasis_keyword() {
        return emphasis_keyword;
    }

    public void setEmphasis_keyword(String emphasis_keyword) {
        this.emphasis_keyword = emphasis_keyword;
    }

    @Override
    public String toString() {
        return "MpTemplateMessage{" +
                "touser='" + touser + '\'' +
                ", template_id='" + template_id + '\'' +
                ", page='" + page + '\'' +
                ", form_id='" + form_id + '\'' +
                ", data=" + data +
                ", color='" + color + '\'' +
                ", emphasis_keyword='" + emphasis_keyword + '\'' +
                '}';
    }
}
