package com.xxg.jwechat.accesstoken;

/**
 * Created by wucao on 16/9/28.
 */
public class AccessToken {

    private String accessToken;
    private int expires;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }
}
