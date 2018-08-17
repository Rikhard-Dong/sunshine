package com.hfmes.sunshine.cache;

import com.hfmes.sunshine.domain.Devc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.statements.SpringRepeat;

import static org.junit.Assert.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/16 19:42
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DevcCacheTest {

    @Test
    public void get() {
        Devc devc = DevcCache.get(2);
        log.info("devc id 2 --> {}", devc);
        devc.setTaskId(0);
        devc.setTaskId(null);
        log.info("devc id 2 --> {}", devc);
        log.info("devc id 2 in DevcCache -> {}", DevcCache.get(2));
        log.info("devc == devc in DevcCache --> {}", devc == DevcCache.get(2));

    }
}