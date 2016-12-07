package com.xxg.jwechat.jsapiticket;

public interface JsApiTicketService {

    /**
     * 获取微信JSAPI Ticket
     * 文档: http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E8.8E.B7.E5.8F.96api_ticket
     * @return JSAPI Ticket
     * @throws Exception
     */
    String getTicket() throws Exception;
}
