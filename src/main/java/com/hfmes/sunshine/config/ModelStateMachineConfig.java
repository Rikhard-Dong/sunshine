package com.hfmes.sunshine.config;

import com.hfmes.sunshine.enums.ModelEvents;
import com.hfmes.sunshine.enums.ModelStatus;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:38
 * <p>
 * 模具状态机
 */
@EnableStateMachineFactory(name = "modelStateMachineFactory")
public class ModelStateMachineConfig extends EnumStateMachineConfigurerAdapter<ModelStatus, ModelEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<ModelStatus, ModelEvents> states) throws Exception {
        states.withStates()
                .states(EnumSet.allOf(ModelStatus.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ModelStatus, ModelEvents> transitions) throws Exception {
    }
}
