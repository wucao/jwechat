package com.xxg.jwechat.jsapiticket;

import com.xxg.jwechat.accesstoken.AccessTokenService;
import com.xxg.jwechat.redis.RedisLock;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * 用于分布式程序获取JsApiTicket
 * 本类并未使用单例模式, 但是必须单示例, 不可以new多次, 建议使用Spring框架来管理
 */
public class SpringRedisJsApiTicketService implements JsApiTicketService {

    private static final String KEY = "xxg:jwechat:jsapiticket";

    private ValueOperations<String, String> valueOperations;

    private AccessTokenService accessTokenService;

    private RedisLock redisLock;

    public void setRedisLock(RedisLock redisLock) {
        this.redisLock = redisLock;
    }

    public void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    public void setValueOperations(ValueOperations<String, String> valueOperations) {
        this.valueOperations = valueOperations;
    }

    @Override
    public String getTicket() throws Exception {
        String ticket  = valueOperations.get(KEY);
        if(ticket == null) {
            if(redisLock.tryLock(KEY + ":lock", 60)) {
                try {
                    JsApiTicket jsApiTicket = JsApiTicketUtil.getTicket(accessTokenService.getAccessToken());
                    ticket = jsApiTicket.getTicket();
                    valueOperations.set(KEY, ticket, jsApiTicket.getExpires() - 60, TimeUnit.SECONDS);
                } finally {
                    redisLock.unlock(KEY + ":lock");
                }
            } else {
                Thread.sleep(500);
                ticket = getTicket();
            }
        }

        return ticket;
    }
}
