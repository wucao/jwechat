package com.xxg.jwechat.user;

import com.xxg.jwechat.WechatException;
import com.xxg.jwechat.accesstoken.AccessTokenService;
import com.xxg.jwechat.util.HttpUtil;
import org.json.JSONObject;

/**
 * Created by wucao on 16/10/19.
 */
public class UserService {

    private AccessTokenService accessTokenService;

    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    /**
     * 根据openId获取关注公众号的用户信息
     * 文档: http://mp.weixin.qq.com/wiki/1/8a5ce6257f1d3b2afb20f83e72b72ce9.html
     * @param openId openId
     * @return 微信用户信息
     */
    public WechatUser getUser(String openId) throws Exception {
        String userInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
        userInfoUrl += accessTokenService.getAccessToken();
        userInfoUrl += "&openid=";
        userInfoUrl += openId;
        userInfoUrl += "&lang=zh_CN";
        String userInfoResult = HttpUtil.get(userInfoUrl);
        JSONObject userInfoJson = new JSONObject(userInfoResult);
        if(!userInfoJson.has("subscribe")) {
            throw new WechatException("根据openId获取用户信息异常: " + userInfoResult);
        }

        WechatUser wechatUser = new WechatUser();

        if(userInfoJson.getInt("subscribe") == 0) { // 用户没有关注公众号
            wechatUser.setSubscribe(false);
        } else {
            wechatUser.setSubscribe(true);
            wechatUser.setNickname(userInfoJson.getString("nickname"));
            int sex = userInfoJson.getInt("sex");
            if(sex == 0) {
                wechatUser.setSex(WechatUserSex.UNKNOWN);
            } else if(sex == 1) {
                wechatUser.setSex(WechatUserSex.MALE);
            } else if(sex == 2) {
                wechatUser.setSex(WechatUserSex.FEMALE);
            } else {
                throw new WechatException("Unknown sex: " + sex);
            }
            wechatUser.setCity(userInfoJson.getString("city"));
            wechatUser.setCountry(userInfoJson.getString("country"));
            wechatUser.setProvince(userInfoJson.getString("province"));
            wechatUser.setHeadImgUrl(userInfoJson.getString("headimgurl"));
            wechatUser.setSubscribeTime(userInfoJson.getLong("subscribe_time"));
            wechatUser.setRemark(userInfoJson.getString("remark"));
        }
        return wechatUser;
    }
}
