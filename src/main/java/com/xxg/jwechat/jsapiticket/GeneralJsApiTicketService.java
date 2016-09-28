package com.xxg.jwechat.jsapiticket;

import com.xxg.jwechat.accesstoken.AccessTokenService;

/**
 * 单机版程序获取微信JsApiTicket, 不可用于分布式程序
 * 本类并未使用单例模式, 但是必须单实例, 不可以new多次, 建议使用Spring框架来管理
 */
public class GeneralJsApiTicketService implements JsApiTicketService {

    private String ticket;
    private long invalidTime;

    private AccessTokenService accessTokenService;

    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    /**
     * 获取微信JSAPI Ticket
     * @return
     */
    public synchronized String getTicket() throws Exception {
        long now = System.currentTimeMillis();
        if(now > invalidTime) {
            JsApiTicket jsApiTicket = JsApiTicketUtil.getTicket(accessTokenService.getAccessToken());
            ticket = jsApiTicket.getTicket();
            invalidTime = now + jsApiTicket.getExpires() * 1000 - 60000;
        }
        return ticket;
    }
}
