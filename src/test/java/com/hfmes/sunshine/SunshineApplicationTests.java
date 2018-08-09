package com.hfmes.sunshine;

import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SunshineApplicationTests {

    @Autowired
    @Qualifier("deviceStateMachines")
    private Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStateMachines;

    @Test
    public void contextLoads() {
        assertNotNull(deviceStateMachines);
        assertNotEquals(deviceStateMachines.size(), 0);
        log.debug("device state machines size is --> {}", deviceStateMachines.size());
    }

}
