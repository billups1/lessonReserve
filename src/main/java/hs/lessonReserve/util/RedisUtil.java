package hs.lessonReserve.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    @Transactional(readOnly = true)
    public String getData(String key) {
        ValueOperations values = redisTemplate.opsForValue();
        return values.get(key) != null ? (String) values.get(key) : "false";
    }

    public void setData(String key, String value) {
        ValueOperations values = redisTemplate.opsForValue();
        values.set(key, value);
    }

    public void setData(String key, String value, Duration duration) {
        ValueOperations values = redisTemplate.opsForValue();
        values.set(key, value, duration);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public void expireValues(String key, Duration duration) {
        redisTemplate.expire(key, duration);
    }

}
