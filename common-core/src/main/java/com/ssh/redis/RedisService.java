package com.ssh.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final String PREFIX = ""; // optional: e.g., "app:v1:"

    private String withPrefix(String key) {
        return PREFIX + key;
    }

    // 🔐 Save with TTL
    public void save(String key, Object value, long ttl, TimeUnit unit) {
        if (value != null) {
            redisTemplate.opsForValue().set(withPrefix(key), value, ttl, unit);
        }
    }

    // 🔐 Save without TTL
    public void save(String key, Object value) {
        if (value != null) {
            redisTemplate.opsForValue().set(withPrefix(key), value);
        }
    }

    // 🧠 Get value with type
    public <T> Optional<T> get(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(withPrefix(key));
        if (value != null && type.isInstance(value)) {
            return Optional.of(type.cast(value));
        }
        return Optional.empty();
    }

    // ✅ Key existence
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(withPrefix(key)));
    }

    // 🧹 Delete key
    public void delete(String key) {
        redisTemplate.delete(withPrefix(key));
    }

    // 🧱 Hash operations
    public void putHash(String key, String field, Object value) {
        redisTemplate.opsForHash().put(withPrefix(key), field, value);
    }

    public Optional<Object> getHashField(String key, String field) {
        return Optional.ofNullable(redisTemplate.opsForHash().get(withPrefix(key), field));
    }

    public Map<Object, Object> getAllHash(String key) {
        return redisTemplate.opsForHash().entries(withPrefix(key));
    }

    public void deleteHashField(String key, String field) {
        redisTemplate.opsForHash().delete(withPrefix(key), field);
    }

    // 🔥 TTL support
    public void expire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(withPrefix(key), timeout, unit);
    }

    public Duration getTTL(String key) {
        return Optional.of(redisTemplate.getExpire(withPrefix(key), TimeUnit.SECONDS))
                .map(Duration::ofSeconds)
                .orElse(Duration.ZERO);
    }
}
