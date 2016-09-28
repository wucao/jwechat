package com.xxg.jwechat.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by wucao on 16/9/28.
 */
public class RedisLock {

    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Redis加锁, 不阻塞线程立即返回
     * @param key 加锁Key
     * @param seconds 加锁时间, 超时会自动释放锁
     * @return 是否成功
     */
    public boolean tryLock(final String key, final int seconds) {
        return (boolean) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] result = (byte[]) connection.execute("set", key.getBytes(), "xxg".getBytes(), "NX".getBytes(), "EX".getBytes(), String.valueOf(seconds).getBytes());
                return result != null && new String(result).equals("OK");
            }
        });
    }

    /**
     * 手动释放锁
     * @param key 加锁Key
     */
    public void unlock(final String key) {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(key.getBytes());
            }
        });
    }

}
