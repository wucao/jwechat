package com.xxg.jwechat.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by wucao on 16/12/9.
 */
public class AuthRedirectService extends AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthRedirectService.class);

    /**
     * 当页面URL包含code参数时, 去掉url上的code参数比跳转
     * @param request
     * @param response
     */
    public void redirectToRemoveCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURL().toString();
        if(request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }
        url = UriComponentsBuilder.fromUriString(url).replaceQueryParam("code", null).toUriString();
        logger.debug("redirectToRemoveCode: " + url);
        response.sendRedirect(url);
    }

    /**
     * 跳转到微信授权页面
     * 文档: http://mp.weixin.qq.com/wiki/4/9ac2e7b1f1d22e9e57260f6553822520.html#.E7.AC.AC.E4.B8.80.E6.AD.A5.EF.BC.9A.E7.94.A8.E6.88.B7.E5.90.8C.E6.84.8F.E6.8E.88.E6.9D.83.EF.BC.8C.E8.8E.B7.E5.8F.96code
     * @param request
     * @param response
     * @param scope snsapi_base/snsapi_userinfo
     * @throws IOException
     */
    public void redirectToWechatAuth(HttpServletRequest request, HttpServletResponse response, Scope scope) throws IOException {
        String backUrl = request.getRequestURL().toString();
        if(request.getQueryString() != null) {
            backUrl += "?" + request.getQueryString();
        }
        backUrl = UriComponentsBuilder.fromUriString(backUrl).replaceQueryParam("code", null).toUriString();
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
        url += wechatConfig.getAppId();
        url += "&redirect_uri=";
        url += URLEncoder.encode(backUrl, "UTF-8");
        url += "&response_type=code&scope=" + scope.getValue() + "#wechat_redirect";
        logger.debug("redirectToWechatAuth: " + url);
        response.sendRedirect(url);
    }
}
