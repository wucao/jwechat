package com.xxg.jwechat.jsapiticket;

/**
 * Created by wucao on 16/9/28.
 */
public class JsApiTicket {

    private String ticket;
    private int expires;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }
}
