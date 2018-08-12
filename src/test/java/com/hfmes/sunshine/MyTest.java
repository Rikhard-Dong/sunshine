package com.hfmes.sunshine;

import com.hfmes.sunshine.enums.DeviceStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 10:34
 */
@Slf4j
public class MyTest {

    @Test
    public void testEnum() {
        DeviceStatus a = DeviceStatus.valueOf("SD00");
        DeviceStatus b = DeviceStatus.SD00;
        log.debug("a --> {}, b --> {}, a == b --> {}", a, b, a == b);
        log.debug("{}", DeviceStatus.SD00);
    }

    @Test
    public void testCapitalize() {
        String a = "ac";
        a = StringUtils.capitalize(a);
        log.debug("{}", a);
    }
}
