package com.hfmes.sunshine;

import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
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

    @Autowired
    @Qualifier("mouldStateMachines")
    private Map<Integer, StateMachine<MouldStatus, MouldEvents>> mouldEventMachines;


    @Test
    public void contextLoads() {
        assertNotNull(deviceStateMachines);
        assertNotEquals(deviceStateMachines.size(), 0);
        log.debug("device state machines size is --> {}", deviceStateMachines.size());

        for (Map.Entry<Integer, StateMachine<DeviceStatus, DeviceEvents>> entry : deviceStateMachines.entrySet()) {
            log.debug("## id --> {}, status --> {}", entry.getKey(), entry.getValue().getState().getId());
        }
        log.debug("##################################################");

        assertNotNull(mouldEventMachines);
        assertNotEquals(mouldEventMachines.size(), 0);
        log.debug("device state machines size is --> {}", mouldEventMachines.size());
        for (Map.Entry<Integer, StateMachine<MouldStatus, MouldEvents>> entry : mouldEventMachines.entrySet()) {
            log.debug("## id --> {}, status --> {}", entry.getKey(), entry.getValue().getState().getId());


        }
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = mouldEventMachines.get(19);
        assertNotNull(mouldStateMachine);
        String state = mouldStateMachine.getState().getId().toString();
        log.debug("mould get state {}, equals --> {}", state, state.equals(MouldStatus.SM10.toString()));
    }

}
