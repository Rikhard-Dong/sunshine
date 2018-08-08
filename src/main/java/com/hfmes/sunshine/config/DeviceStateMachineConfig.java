package com.hfmes.sunshine.config;

import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:39
 */
@Configuration
@EnableStateMachineFactory(name = "DeviceStateMachineFactory")
@Slf4j
public class DeviceStateMachineConfig extends EnumStateMachineConfigurerAdapter<DeviceStatus, DeviceEvents> {


    @Override
    public void configure(StateMachineStateConfigurer<DeviceStatus, DeviceEvents> states) throws Exception {
        log.debug("初始化DeviceStateMachine状态");

        states.withStates()
                .states(EnumSet.allOf(DeviceStatus.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<DeviceStatus, DeviceEvents> transitions) throws Exception {
        log.debug("设置DeviceStateMachine状态事件及动作");

    }
}
