package com.xxg.jwechat.auth;

import com.xxg.jwechat.user.WechatUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信授权事件回调
 */
public interface WechatAuthEventHandle {

    /**
     * snsapi_base微信授权获取到openId事件
     * @param openId
     * @return 是否需要继续snsapi_userinfo授权获取完整信息, 返回false不会继续调用userInfoHandle方法
     */
    boolean openIdHandle(HttpServletRequest request, HttpServletResponse response, String openId);

    /**
     * snsapi_userinfo微信授权获取到微信用户信息事件
     * @param wechatUser
     */
    void userInfoHandle(HttpServletRequest request, HttpServletResponse response, WechatUser wechatUser);

}
