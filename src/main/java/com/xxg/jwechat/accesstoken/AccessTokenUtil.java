package com.xxg.jwechat.accesstoken;

import com.xxg.jwechat.WechatException;
import com.xxg.jwechat.util.HttpUtil;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by wucao on 16/9/28.
 */
public class AccessTokenUtil {

    public static AccessToken getAccessToken(String appId, String appSecret) throws IOException, WechatException {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
        String result = HttpUtil.get(url);
        JSONObject json = new JSONObject(result);
        if(json.has("access_token")) {
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(json.getString("access_token"));
            accessToken.setExpires(json.getInt("expires_in"));
            return accessToken;
        } else {
            throw new WechatException("获取access_token失败: " + result);
        }
    }
}
