package com.xxg.jwechat.auth;

import com.xxg.jwechat.Constants.Constants;
import com.xxg.jwechat.user.WechatUser;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信授权拦截器, 先使用snsapi_base授权获取openId, 根据openId通过微信用户信息接口以及其他方式(如DB)查询用户信息,
 * 如果查询不到用户信息, 则再次使用snsapi_userinfo弹出授权窗口, 通过用户授权的方式获取用户信息
 */
public class AutoWechatAuthInterceptor extends HandlerInterceptorAdapter {

    private AuthRedirectService authRedirectService;

    private WechatAuthEventHandle wechatAuthEventHandle;

    public void setAuthRedirectService(AuthRedirectService authRedirectService) {
        this.authRedirectService = authRedirectService;
    }

    public void setWechatAuthEventHandle(WechatAuthEventHandle wechatAuthEventHandle) {
        this.wechatAuthEventHandle = wechatAuthEventHandle;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String code = request.getParameter("code");
        String openId = (String) request.getSession().getAttribute(Constants.OPENID_SESSION_KEY);
        if(openId == null) {

            if(code == null) {
                // 跳转微信授权页面
                authRedirectService.redirectToWechatAuth(request, response, Scope.SNSAPI_BASE);
                return false;
            } else {
                AuthBaseInfo authBaseInfo = authRedirectService.getBaseInfo(code);

                if(wechatAuthEventHandle.openIdHandle(request, response, authBaseInfo.getOpenId())) {

                    try {
                        WechatUser wechatUser = authRedirectService.getUserInfo(authBaseInfo.getOpenId(), authBaseInfo.getAccessToken());
                        wechatAuthEventHandle.userInfoHandle(request, response, wechatUser);
                    } catch (Exception e) {
                        authRedirectService.redirectToWechatAuth(request, response, Scope.SNSAPI_USERINFO);
                        return false;
                    }

                }

                request.getSession().setAttribute(Constants.OPENID_SESSION_KEY, authBaseInfo.getOpenId());

                // 成功, 去除code参数跳转
                authRedirectService.redirectToRemoveCode(request, response);

                return false;
            }

        } else {
            // openid不为空, 不需要进行微信授权

            if(code == null) {
                // 成功
                return true;
            } else {

                // 成功, 去除code参数跳转
                authRedirectService.redirectToRemoveCode(request, response);
                return false;
            }
        }

    }
}
