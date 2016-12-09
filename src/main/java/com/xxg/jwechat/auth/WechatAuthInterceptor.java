package com.xxg.jwechat.auth;

import com.xxg.jwechat.Constants.Constants;
import com.xxg.jwechat.user.WechatUser;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信授权拦截器
 */
public class WechatAuthInterceptor extends HandlerInterceptorAdapter {

    private AuthRedirectService authRedirectService;

    private Scope scope;

    private WechatAuthEventHandle wechatAuthEventHandle;

    public void setScope(Scope scope) {
        this.scope = scope;
    }

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
                authRedirectService.redirectToWechatAuth(request, response, scope);
                return false;
            } else {
                // 对code校验
                boolean getUserInfo = false;
                AuthBaseInfo authBaseInfo = authRedirectService.getBaseInfo(code);
                if(wechatAuthEventHandle != null) {
                    getUserInfo = wechatAuthEventHandle.openIdHandle(authBaseInfo.getOpenId());
                }
                if(scope == Scope.SNSAPI_USERINFO && getUserInfo) {
                    WechatUser wechatUser = authRedirectService.getUserInfo(authBaseInfo.getOpenId(), authBaseInfo.getAccessToken());
                    wechatAuthEventHandle.userInfoHandle(wechatUser);
                }
                request.getSession().setAttribute(Constants.OPENID_SESSION_KEY, authBaseInfo.getOpenId());
                // 成功, 去除code参数
                authRedirectService.redirectToRemoveCode(request, response);
                return false;
            }

        } else {
            // openid不为空, 不需要进行微信授权

            if(code == null) {
                // 成功
                return true;
            } else {

                // 成功, 去除code参数
                authRedirectService.redirectToRemoveCode(request, response);
                return false;
            }
        }

    }
}
