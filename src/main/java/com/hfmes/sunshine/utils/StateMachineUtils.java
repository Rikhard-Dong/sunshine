package com.hfmes.sunshine.utils;

import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 15:25
 * 状态机工具类
 */

@Slf4j
public class StateMachineUtils {

    /**
     * 恢复设备状态机到指定状态
     *
     * @param stateMachine 状态机
     * @param status       状态
     */
    public static void setDeviceStateMachineState(StateMachine<DeviceStatus, DeviceEvents> stateMachine,
                                                  DeviceStatus status) {

        log.debug("###### before update device state machine  status --> {}", stateMachine.getState().getId());
        stateMachine.stop();
        List<StateMachineAccess<DeviceStatus, DeviceEvents>> regions = stateMachine.getStateMachineAccessor().withAllRegions();
        for (StateMachineAccess<DeviceStatus, DeviceEvents> region : regions) {
            region.resetStateMachine(new DefaultStateMachineContext<>(status, null, null, null));
        }
        stateMachine.start();

        log.debug("###### after update device state machine status --> {}", stateMachine.getState().getId());
    }

    /**
     * 恢复模具状态机到指定状态
     *
     * @param stateMachine 状态机
     * @param mouldEvents  状态
     */
    public static void setMouldStateMachineState(StateMachine<MouldStatus, MouldEvents> stateMachine,
                                                 MouldStatus mouldEvents) {
        log.debug("###### before update mould state machine  status --> {}", stateMachine.getState().getId());
        stateMachine.stop();
        List<StateMachineAccess<MouldStatus, MouldEvents>> regions = stateMachine.getStateMachineAccessor().withAllRegions();
        for (StateMachineAccess<MouldStatus, MouldEvents> region : regions) {
            region.resetStateMachine(new DefaultStateMachineContext<>(mouldEvents, null, null, null));
        }
        stateMachine.start();

        log.debug("###### after update mould state machine status --> {}", stateMachine.getState().getId());

    }
}
