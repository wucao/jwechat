package com.xxg.jwechat.auth;

/**
 * Created by wucao on 16/12/7.
 */
public class AuthBaseInfo {

    private String accessToken;
    private String openId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
