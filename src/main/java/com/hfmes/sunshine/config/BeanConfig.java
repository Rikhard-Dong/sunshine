package com.hfmes.sunshine.config;

import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.Person;
import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 8:42
 */
@Configuration
@Slf4j
public class BeanConfig {

    @Autowired
    private StateMachineFactory<DeviceStatus, DeviceEvents> deviceStateMachineFactory;

    @Autowired
    private DevcDao deviceDao;

    @Bean(name = "devcs")
    public Map<Integer, Devc> devcs() {
        Map<Integer, Devc> devcs = new ConcurrentHashMap<>();

        return devcs;
    }

    @Bean(name = "persons")
    public Map<String, Person> persons() {
        Map<String, Person> persons = new ConcurrentHashMap<>();

        return persons;
    }

    @Bean(name = "deviceStateMachines")
    public Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStateMachines() {
        return initDeviceStateMachines();
    }

    private Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> initDeviceStateMachines() {
        Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStateMachines = new ConcurrentHashMap<>();

        if(deviceStateMachineFactory == null) {
            log.error("device state machine factory can't be null");
            return null;
        }

        List<Devc> devcs = deviceDao.findAll();
        if (devcs != null && devcs.size() != 0) {
            for (Devc devc : devcs) {
                StateMachine<DeviceStatus, DeviceEvents> stateMachine = deviceStateMachineFactory.getStateMachine(devc.getTitle());
                // TODO 恢复到指定状态
                stateMachine.start();
                deviceStateMachines.put(devc.getDeviceId(), stateMachine);
            }
        } else {
            log.debug("devcs 为null 或者 size 为0");
        }

        return deviceStateMachines;
    }
}
