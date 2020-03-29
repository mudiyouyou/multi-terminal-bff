package net.multi.terminal.bff.model;

import net.multi.terminal.bff.core.constant.CoreKeys;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Api会话类
 * 通过Redis保存会话
 * Key为前缀+SessionId
 * Value为Hash类型，保存自定义的各种会话上下文数据
 */
public class ApiSession {
    private final BoundHashOperations ops;
    private String key;

    public ApiSession(String sessionId, RedisTemplate redisTemplate) {
        this.key = CoreKeys.API_SESSIONID + ":" + sessionId;
        ops = redisTemplate.boundHashOps(this.key);
        ops.expire(24, TimeUnit.HOURS);
    }

    public void put(String key, Object value) {
        ops.put(key, value);
    }

    public Long delete(String... objects) {
        return ops.delete(objects);
    }

    public Boolean hasKey(String o) {
        return ops.hasKey(o);
    }

    public Object get(String o) {
        return ops.get(o);
    }

    public Set keys() {
        return ops.keys();
    }

    public Long size() {
        return ops.size();
    }

    public Boolean putIfAbsent(String o, Object o2) {
        return ops.putIfAbsent(o, o2);
    }

    public Boolean expire(long l, TimeUnit timeUnit) {
        return ops.expire(l, timeUnit);
    }

    public Boolean expireAt(Date date) {
        return ops.expireAt(date);
    }

    public void rename(String o) {
        ops.rename(o);
    }

    public String getSessionId() {
        return key.split(":")[1];
    }
}
