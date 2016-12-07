package com.xxg.jwechat.auth;

import com.xxg.jwechat.WechatConfig;
import com.xxg.jwechat.WechatException;
import com.xxg.jwechat.user.WechatUser;
import com.xxg.jwechat.user.WechatUserSex;
import com.xxg.jwechat.util.HttpUtil;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by wucao on 16/12/7.
 */
public class AuthService {

    private WechatConfig wechatConfig;

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    /**
     * 根据微信授权回调的code参数获取openId和其他基础信息
     * 文档: http://mp.weixin.qq.com/wiki/4/9ac2e7b1f1d22e9e57260f6553822520.html#.E7.AC.AC.E4.BA.8C.E6.AD.A5.EF.BC.9A.E9.80.9A.E8.BF.87code.E6.8D.A2.E5.8F.96.E7.BD.91.E9.A1.B5.E6.8E.88.E6.9D.83access_token
     * @param code 微信授权回调的code参数
     * @return base信息(包含openId)
     */
    public AuthBaseInfo getBaseInfo(String code) throws IOException, WechatException {
        String checkCodeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=";
        checkCodeUrl += wechatConfig.getAppId();
        checkCodeUrl += "&secret=";
        checkCodeUrl += wechatConfig.getAppSecret();
        checkCodeUrl += "&code=";
        checkCodeUrl += code;
        checkCodeUrl += "&grant_type=authorization_code";
        String json = HttpUtil.get(checkCodeUrl);
        JSONObject jsonObject = new JSONObject(json);
        if(jsonObject.has("openid")) {
            AuthBaseInfo authBaseInfo = new AuthBaseInfo();
            authBaseInfo.setOpenId(jsonObject.getString("openid"));
            authBaseInfo.setAccessToken(jsonObject.getString("access_token"));
            return authBaseInfo;
        } else {
            throw new WechatException("获取微信用户openId异常: " + json);
        }
    }

    /**
     * 根据openId和用户授权得到的accessToken获取用户信息, 注意: 无法获取到用户是否关注微信公众以及关注时间
     * 文档: http://mp.weixin.qq.com/wiki/4/9ac2e7b1f1d22e9e57260f6553822520.html#.E7.AC.AC.E5.9B.9B.E6.AD.A5.EF.BC.9A.E6.8B.89.E5.8F.96.E7.94.A8.E6.88.B7.E4.BF.A1.E6.81.AF.28.E9.9C.80scope.E4.B8.BA_snsapi_userinfo.29
     * @param openId openId
     * @param accessToken accessToken
     * @return 用户信息
     */
    public WechatUser getUserInfo(String openId, String accessToken) throws IOException, WechatException {
        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + accessToken + "&openid=" + openId + "&lang=zh_CN";
        String json = HttpUtil.get(userInfoUrl);
        JSONObject jsonObject = new JSONObject(json);
        if(jsonObject.has("errcode")) {
            throw new WechatException("获取授权微信用户信息异常: " + json);
        }

        WechatUser wechatUser = new WechatUser();
        wechatUser.setNickname(jsonObject.getString("nickname"));
        int sex = jsonObject.getInt("sex");
        if(sex == 0) {
            wechatUser.setSex(WechatUserSex.UNKNOWN);
        } else if(sex == 1) {
            wechatUser.setSex(WechatUserSex.MALE);
        } else if(sex == 2) {
            wechatUser.setSex(WechatUserSex.FEMALE);
        } else {
            throw new WechatException("Unknown sex: " + sex);
        }
        wechatUser.setCity(jsonObject.getString("city"));
        wechatUser.setCountry(jsonObject.getString("country"));
        wechatUser.setProvince(jsonObject.getString("province"));
        wechatUser.setHeadImgUrl(jsonObject.getString("headimgurl"));

        return wechatUser;
    }

}
