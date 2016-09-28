package com.xxg.jwechat.accesstoken;

import com.xxg.jwechat.WechatConfig;

/**
 * 单机版程序获取微信AccessToken, 不可用于分布式程序
 * 本类并未使用单例模式, 但是必须单实例, 不可以new多次, 建议使用Spring框架来管理
 */
public class GeneralAccessTokenService implements AccessTokenService {

    private String token;
    private long invalidTime;

    private WechatConfig wechatConfig;

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    @Override
    public synchronized String getAccessToken() throws Exception {
        long now = System.currentTimeMillis();
        if(now > invalidTime) {
            AccessToken accessToken = AccessTokenUtil.getAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
            token = accessToken.getAccessToken();
            invalidTime = now + accessToken.getExpires() * 1000 - 60000;
        }
        return token;
    }
}
