package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
import com.hfmes.sunshine.service.StatusChangeService;
import com.hfmes.sunshine.utils.StateMachineUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/12 10:39
 */
@Service
public class StatusChangeServiceImpl implements StatusChangeService {

    private final Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStatemachineMap;

    private final Map<Integer, StateMachine<MouldStatus, MouldEvents>> mouldStatemachienMap;

    @Autowired
    public StatusChangeServiceImpl(@Qualifier("deviceStateMachines") Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStatemachineMap,
                                   @Qualifier("mouldStateMachines") Map<Integer, StateMachine<MouldStatus, MouldEvents>> mouldStatemachienMap) {
        this.deviceStatemachineMap = deviceStatemachineMap;
        this.mouldStatemachienMap = mouldStatemachienMap;
    }

    /**
     * @param deviceId  设备id
     * @param statusStr 转换状态
     */
    @Override
    public void changeDeviceStateMachineStatus(Integer deviceId, String statusStr) {
        StateMachine<DeviceStatus, DeviceEvents> stateMachine = deviceStatemachineMap.get(deviceId);
        if (stateMachine == null) {
            return;
        }
        DeviceStatus status;
        try {
            status = DeviceStatus.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        StateMachineUtils.setDeviceStateMachineState(stateMachine, status);
    }

    /**
     * @param mouldId   模具id
     * @param statusStr 转换状态
     */
    @Override
    public void changeMouldStateMachineStatus(Integer mouldId, String statusStr) {
        StateMachine<MouldStatus, MouldEvents> stateMachine = mouldStatemachienMap.get(mouldId);
        if (stateMachine == null) {
            return;
        }

        MouldStatus status;

        try {
            status = MouldStatus.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        StateMachineUtils.setMouldStateMachineState(stateMachine, status);
    }
}
