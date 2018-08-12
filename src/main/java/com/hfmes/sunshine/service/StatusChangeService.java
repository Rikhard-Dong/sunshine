package com.hfmes.sunshine.service;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/12 10:29
 * 实现管理端对状态机进行状态转换
 */
public interface StatusChangeService {

    /**
     * 强制更新设备状态
     *
     * @param deviceId 设备id
     * @param status   更新状态
     */
    void changeDeviceStateMachineStatus(Integer deviceId, String status);

    /**
     * 强制更新模具状态机状态
     *
     * @param mouldId 模具id
     * @param status  模具状态
     */
    void changeMouldStateMachineStatus(Integer mouldId, String status);

}
