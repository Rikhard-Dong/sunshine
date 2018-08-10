package com.hfmes.sunshine.utils;

import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 15:25
 * 状态机工具类
 */
public class StateMachineUtils {

    /**
     * 恢复设备状态机到指定状态
     *
     * @param stateMachine 状态机
     * @param status       转态
     */
    public static StateMachine<DeviceStatus, DeviceEvents> setDeviceStateMachineState(StateMachine<DeviceStatus, DeviceEvents> stateMachine,
                                                  DeviceStatus status) {
        stateMachine.stop();
        List<StateMachineAccess<DeviceStatus, DeviceEvents>> regions = stateMachine.getStateMachineAccessor().withAllRegions();
        for (StateMachineAccess<DeviceStatus, DeviceEvents> region : regions) {
            region.resetStateMachine(new DefaultStateMachineContext<>(status, null, null, null));
        }
        stateMachine.start();

        return stateMachine;
    }
}