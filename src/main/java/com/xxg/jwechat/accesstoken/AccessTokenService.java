package com.xxg.jwechat.accesstoken;

/**
 * Created by wucao on 16/9/28.
 */
public interface AccessTokenService {

    /**
     * 获取微信Access Token
     * 相关文档: http://mp.weixin.qq.com/wiki/14/9f9c82c1af308e3b14ba9b973f99a8ba.html
     * @return Access Token
     * @throws Exception
     */
    String getAccessToken() throws Exception;
}
