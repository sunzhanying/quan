package com.jeesite.API.weixin.api;

import com.jeesite.API.weixin.bean.BaseResult;
import com.jeesite.API.weixin.util.JsonUtil;
import com.jeesite.API.weixin.bean.menu.Menu;
import com.jeesite.API.weixin.bean.menu.MenuButtons;
import com.jeesite.API.weixin.client.LocalHttpClient;
import com.jeesite.API.weixin.bean.menu.TrymatchResult;
import com.jeesite.API.weixin.bean.menu.selfmenu.CurrentSelfmenuInfo;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

import java.nio.charset.Charset;

public class MenuAPI extends BaseAPI {

    /**
     * 创建菜单
     *
     * @param access_token
     * @param menuJson     菜单json 数据 例如{\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"歌手简介\",\"key\":\"V1001_TODAY_SINGER\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com/\"},{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http://v.qq.com/\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]}]}
     * @return
     */
    public static BaseResult menuCreate(String access_token, String menuJson) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(BASE_URI + "/cgi-bin/menu/create")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity(menuJson, Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, BaseResult.class);
    }

    /**
     * 创建菜单
     *
     * @param access_token
     * @param menuButtons
     * @return
     */
    public static BaseResult menuCreate(String access_token, MenuButtons menuButtons) {
        String str = JsonUtil.toJSONString(menuButtons);
        return menuCreate(access_token, str);
    }

    /**
     * 获取菜单
     *
     * @param access_token
     * @return
     */
    public static Menu menuGet(String access_token) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/cgi-bin/menu/get")
                .addParameter("access_token", access_token)
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, Menu.class);
    }

    /**
     * 删除菜单
     *
     * @param access_token
     * @return
     */
    public static BaseResult menuDelete(String access_token) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/cgi-bin/menu/delete")
                .addParameter("access_token", access_token)
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, BaseResult.class);
    }


    /**
     * 获取自定义菜单配置
     * 本接口将会提供公众号当前使用的自定义菜单的配置，
     * 如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，
     * 而如果公众号是在公众平台官网通过网站功能发布菜单，
     * 则本接口返回运营者设置的菜单配置。
     *
     * @param access_token
     * @return
     */
    public static CurrentSelfmenuInfo get_current_selfmenu_info(String access_token) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/cgi-bin/get_current_selfmenu_info")
                .addParameter("access_token", access_token)
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, CurrentSelfmenuInfo.class);
    }

    /**
     * 创建个性化菜单
     *
     * @param access_token
     * @param menuButtons
     * @return
     */
    public static BaseResult menuAddconditional(String access_token, MenuButtons menuButtons) {
        String menuJson = JsonUtil.toJSONString(menuButtons);
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(BASE_URI + "/cgi-bin/menu/addconditional")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity(menuJson, Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, BaseResult.class);
    }

    /**
     * 删除个性化菜单
     *
     * @param access_token
     * @param menuid
     * @return
     */
    public static BaseResult menuDelconditional(String access_token, String menuid) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(BASE_URI + "/cgi-bin/menu/delconditional")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity("{\"menuid\":\"" + menuid + "\"}", Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, BaseResult.class);
    }

    /**
     * 测试个性化菜单匹配结果
     *
     * @param access_token
     * @param user_id      可以是粉丝的OpenID，也可以是粉丝的微信号。
     * @return
     */
    public static TrymatchResult menuTrymatch(String access_token, String user_id) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(BASE_URI + "/cgi-bin/menu/trymatch")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity("{\"user_id\":\"" + user_id + "\"}", Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, TrymatchResult.class);
    }


}