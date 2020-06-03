package com.jeesite.API.weixin.bean.user;

import com.jeesite.API.weixin.bean.BaseResult;

import java.util.List;

public class UserInfoList extends BaseResult {

    private List<WxUser> wxUser_info_list;

    public List<WxUser> getUser_info_list() {
        return wxUser_info_list;
    }

    public void setUser_info_list(List<WxUser> wxUser_info_list) {
        this.wxUser_info_list = wxUser_info_list;
    }

}
