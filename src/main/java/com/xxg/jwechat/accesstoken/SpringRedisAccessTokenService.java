package com.xxg.jwechat.accesstoken;

import com.xxg.jwechat.WechatConfig;
import com.xxg.jwechat.redis.RedisLock;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * 用于分布式程序获取AccessToken
 * 本类并未使用单例模式, 但是必须单示例, 不可以new多次, 建议使用Spring框架来管理
 */
public class SpringRedisAccessTokenService implements AccessTokenService {

    private static final String KEY = "xxg:jwechat:accesstoken";

    private WechatConfig wechatConfig;

    private ValueOperations<String, String> valueOperations;

    private RedisLock redisLock;

    public void setRedisLock(RedisLock redisLock) {
        this.redisLock = redisLock;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    public void setValueOperations(ValueOperations<String, String> valueOperations) {
        this.valueOperations = valueOperations;
    }

    @Override
    public String getAccessToken() throws Exception {

        String accessToken  = valueOperations.get(KEY);
        if(accessToken == null) {
            if(redisLock.tryLock(KEY + ":lock", 60)) {
                try {
                    AccessToken result = AccessTokenUtil.getAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret());
                    accessToken = result.getAccessToken();
                    valueOperations.set(KEY, accessToken, result.getExpires() - 60, TimeUnit.SECONDS);
                } finally {
                    redisLock.unlock(KEY + ":lock");
                }
            } else {
                Thread.sleep(500);
                accessToken = getAccessToken();
            }
        }

        return accessToken;
    }
}
