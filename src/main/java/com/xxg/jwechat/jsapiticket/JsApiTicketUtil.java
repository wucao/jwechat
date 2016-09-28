package com.xxg.jwechat.jsapiticket;

import com.xxg.jwechat.util.HttpUtil;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by wucao on 16/9/28.
 */
public class JsApiTicketUtil {

    public static JsApiTicket getTicket(String accessToken) throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
        JSONObject json = new JSONObject(HttpUtil.get(url));
        JsApiTicket jsApiTicket = new JsApiTicket();
        jsApiTicket.setTicket(json.getString("ticket"));
        jsApiTicket.setExpires(json.getInt("expires_in"));
        return jsApiTicket;
    }
}
