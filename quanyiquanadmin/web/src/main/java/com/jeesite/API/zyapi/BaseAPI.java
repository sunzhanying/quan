package com.jeesite.API.zyapi;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;

import java.util.Date;

/**
 * 智廷开放平台开发者
 */
public abstract class BaseAPI {

    protected static final String BASE_URI = "https://area22-win.pospal.cn:443/pospal-api2/openapi/v1";

    protected static Header jsonHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
    protected static Header userAgentHeader = new BasicHeader("User-Agent", "openApi");
    protected static Header acceptHeader = new BasicHeader("accept-encoding", "gzip,deflate");
    protected static Header timeStampHeader = new BasicHeader("time-stamp", String.valueOf((new Date()).getTime() / 1000L));


}
