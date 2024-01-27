package hs.lessonReserve.redis;

import hs.lessonReserve.util.RedisUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

//@SpringBootTest
public class RedisTest {

    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofSeconds(5);

    @Autowired
    private RedisUtil redisUtil;

    @BeforeEach
    void shutDown() {
        redisUtil.setData(KEY, VALUE, DURATION);
    }

    @AfterEach
    void tearDown() {
        redisUtil.deleteData(KEY);
    }

    @Test
    @DisplayName("Redis에 데이터를 저장하면 정상적으로 조회된다.")
    void saveAndFindTest() {
        String findValue = redisUtil.getData(KEY);
        //then
        Assertions.assertThat(VALUE).isEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis 데이터 수정")
    void updateTest() {
        // given
        String updateValue = "updateValue";
        redisUtil.setData(KEY, updateValue);

        // when
        String findValue = redisUtil.getData(KEY);

        // then
        Assertions.assertThat(updateValue).isEqualTo(findValue);
        Assertions.assertThat(VALUE).isNotEqualTo(findValue);
    }

    @Test
    void deleteTest() {
        // when
        redisUtil.deleteData(KEY);
        String findValue = redisUtil.getData(KEY);
        // then
        Assertions.assertThat(findValue).isEqualTo("false");
    }

    @Test
    void expireTest() {
        // when
        redisUtil.expireValues(KEY, DURATION);
        // then
        String findValueImmediately = redisUtil.getData(KEY);
        await().pollDelay(Duration.ofSeconds(6)).untilAsserted(
                () ->{
                    String findDataWait = redisUtil.getData(KEY);
                    Assertions.assertThat(findDataWait).isEqualTo("false");
                }
        );
    }

}
