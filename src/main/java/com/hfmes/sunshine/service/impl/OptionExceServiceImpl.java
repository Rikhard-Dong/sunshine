package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.MldDtl;
import com.hfmes.sunshine.enums.*;
import com.hfmes.sunshine.service.OptionExceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 13:52
 */
@Service
@Slf4j
public class OptionExceServiceImpl implements OptionExceService {

    private final Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStateMachineMap;
    private final Map<Integer, StateMachine<MouldStatus, MouldEvents>> mouldStateMachineMap;

    @Qualifier("devcs")
    private final Map<Integer, Devc> devcMap;
    @Qualifier("mldDtls")
    private final Map<Integer, MldDtl> mldDtlMap;

    @Autowired
    public OptionExceServiceImpl(@Qualifier("deviceStateMachines")
                                         Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStateMachineMap,
                                 @Qualifier("mouldStateMachines")
                                         Map<Integer, StateMachine<MouldStatus, MouldEvents>> mouldStateMachineMap,
                                 Map<Integer, Devc> descMap,
                                 Map<Integer, MldDtl> mldDtlMap) {
        this.deviceStateMachineMap = deviceStateMachineMap;
        this.mouldStateMachineMap = mouldStateMachineMap;
        this.devcMap = descMap;
        this.mldDtlMap = mldDtlMap;
    }


    /**
     * opId --> 1 装模/料
     * <p>
     * 执行操作:
     * |---  1. 模具状态改变, 从领用状态(SM10)变为装模状态(SM20), 并记录状态转换信息
     * |---  2. 更新模具状态信息, 从领用状态(SM10)变为装模状态(SM20), 并同步更新内存数据
     * |---  3. 为设备绑定模具id, 设置模具状态, 并同步内存数据
     * |---  4. 记录MldLog日志(对应action中)
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void mouldFilling(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = getMouldStateMachine(mldDtlId, MouldStatus.SM10);
        Message<MouldEvents> message = getMessage(MouldEvents.START_MOULD_FILLING, opId, optionId, devcId, mldDtlId);
                /*MessageBuilder
                .withPayload(MouldEvents.START_MOULD_FILLING)
                .setHeader("opId", opId)
                .setHeader("mldId", mldDtlId)
                .setHeader("devcId", devcId)
                .setHeader("optionId", optionId)
                .build();*/

        mouldStateMachine.sendEvent(message);
    }

    /**
     * opId --> 2 装模完成
     * 执行操作
     * |--- 1. 对应模具状态机从装模SM20到使用SM40, 并记录转换信息
     * |--- 2. 更新对应模具状态以及同步到内存数据中,
     * |--- 3. 更新设备中模具对应的状态, 同步内存数据
     * |--- 4. 记录对应mldLog操作日志(对应action中)
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void completeMouldFilling(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = getMouldStateMachine(mldDtlId, MouldStatus.SM20);
        Message<MouldEvents> message = getMessage(MouldEvents.COMPLETE_MOULD_FILLING, opId, optionId, devcId, mldDtlId);
        mouldStateMachine.sendEvent(message);
    }

    /**
     * opId --> 3 卸模/料
     * 模具状态从使用状态到卸模
     * 执行操作
     * |--- 1. 对应模具状态机从使用SM40到卸模SM30状态并记录状态转换信息
     * |--- 2. 记录对应mldLog操作日志
     * |--- 3. 更新模具表中模具的状态信息, 同步内存数据
     * |--- 4. 更新对应设备表中设备的模具状态信息, 同步内存数据
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void demoulding(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = getMouldStateMachine(mldDtlId, MouldStatus.SM40);
        Message<MouldEvents> message = getMessage(MouldEvents.START_DEMOULDING, opId, optionId, devcId, mldDtlId);
        mouldStateMachine.sendEvent(message);
    }

    /**
     * opId --> 4  卸模完成
     * 执行操作:
     * |--- 1. 对应模具状态机状态从卸模状态SM30转换到SM10领用状态, 并记录状态转换记录
     * |--- 2. 记录对应的mldLog日志信息
     * |--- 3. 更新模具表中模具的状态, 同步内存数据
     * |--- 4. 将设备表中模具id, 模具状态置空, 同步内存数据
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void completeDemoulding(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = getMouldStateMachine(mldDtlId, MouldStatus.SM30);
        Message<MouldEvents> message = getMessage(MouldEvents.COMPLETE_DEMOULDING, opId, optionId, devcId, mldDtlId);
        mouldStateMachine.sendEvent(message);
    }

    /**
     * opId --> 5 模具维修
     * 执行动作:
     * |--- 1. 对应模具状态机从故障状态SM50转换到模具检修SM60状态, 并记录状态转换记录
     * |--- 2. 记录对应mldLog日志信息
     * |--- 3. 更新模具信息, 并同步到内存
     * |--- 4. 设备有模具的话同时更新设备模具状态, 并同步到内存数据中
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void mouldRepair(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = getMouldStateMachine(mldDtlId, MouldStatus.SM50);
        Message<MouldEvents> message = getMessage(MouldEvents.MOULD_REPAIR, opId, optionId, devcId, mldDtlId);
        mouldStateMachine.sendEvent(message);
    }

    /**
     * opId --> 6  卸模/料
     * 模具状态从模具检修到模具检修
     * 执行动作
     * |--- 1. 模具状态机从模具检修SM60到模具检修SM60, 不记录状态转换
     * |--- 2. 记录mldLog日志信息
     * |--- 3. 去除设备中的模具信息, 并同步到内存中
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void demoulding2(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = getMouldStateMachine(mldDtlId, MouldStatus.SM60);
        Message<MouldEvents> message = getMessage(MouldEvents.DEMOULDING, opId, optionId, devcId, mldDtlId);
        mouldStateMachine.sendEvent(message);
    }

    /**
     * opId --> 7 模具修复
     * 从模具检修到使用
     * 执行动作
     * |--- 1. 模具状态从模具检修SM60转换到使用SM40, 并记录状态转换
     * |--- 2. 记录mldLog日志信息
     * |--- 3. 更新模具状态, 同步到内存中
     * |--- 4. 更新设备中的模具信息
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void mouldRepairComplete(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = getMouldStateMachine(mldDtlId, MouldStatus.SM60);
        Message<MouldEvents> message = getMessage(MouldEvents.MOULD_REPAIR_COMPLETE2SM40, opId, optionId, devcId, mldDtlId);
        mouldStateMachine.sendEvent(message);
    }

    /**
     * opId --> 8 模具修复
     * 从模具检修到领用
     * 执行动作
     * |--- 1. 模具状态从模具检修SM60转换到领用SM10状态, 并记录状态转换
     * |--- 2. 记录mldLog日志信息
     * |--- 3. 更新模具状态, 同步到内存
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void mouldRepairComplete2(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = getMouldStateMachine(mldDtlId, MouldStatus.SM60);
        Message<MouldEvents> message = getMessage(MouldEvents.MOULD_REPAIR_COMPLETE2SM10, opId, optionId, devcId, mldDtlId);
        mouldStateMachine.sendEvent(message);
    }

    /**
     * opId --> 9 开始生产
     * 执行动作
     * |--- 1. 设备状态机从待机SD00转换到运行SD10, 记录状态转换
     * |--- 2. 记录devcLog日志信息
     * |--- 3. 更新设备信息, 并同步到设备中
     * |--- 4. 更新工单状态, 从tasks和deviceTasks中移除该工单
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void startProduce(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        // TODO 待完成
    }

    /**
     * opId --> 10 继续生产
     * 执行操作
     * |--- 1. 设备状态从待机SD00转换到运行SD10, 并记录状态转换
     * |--- 2. 记录devLog日志
     * |--- 3. 更新工单状态, 暂停ST20 -> 执行ST10
     * |--- 4. 更新设备的状态, 工单信息
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void continueProduce(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD00);
        Message<DeviceEvents> message = getMessage(DeviceEvents.PRODUCE_CONTINUE, opId, optionId, devcId, mldDtlId,
                TaskStatus.ST20.toString(), TaskStatus.ST10.toString());
        deviceStateMachine.sendEvent(message);
    }

    /**
     * opId --> 11 生产完成
     * 执行操作
     * |--- 1. 设备状态从运行SD10到待机SD00, 并记录状态转换
     * |--- 2. 记录devLog日志
     * |--- 3. 更新工单状态 执行ST10 -> 待验收ST30, 并记录状态转换
     * |--- 4. 更新设备状态, 工单
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void completeProduce(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD10);
        Message<DeviceEvents> message = getMessage(DeviceEvents.PRODUCE_COMPLETE, opId, optionId, devcId, mldDtlId,
                TaskStatus.ST10.toString(), TaskStatus.ST30.toString());
        deviceStateMachine.sendEvent(message);
    }

    /**
     * opId --> 12 暂停生产
     * 执行操作
     * |--- 1. 设备状态从运行SD10 -> 待机SD00, 并记录状态转换
     * |--- 2. 记录devLog日志
     * |--- 3. 更新工单状态 ST10 -> ST20 并记录状态转换
     * |--- 4. 更新设备状态, 工单
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void suspendProduce(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD10);
        Message<DeviceEvents> message = getMessage(DeviceEvents.PRODUCE_SUSPEND, opId, optionId, devcId, mldDtlId,
                TaskStatus.ST10.toString(), TaskStatus.ST20.toString());
        deviceStateMachine.sendEvent(message);

    }

    /**
     * opId --> 13 设备故障
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void deviceFault(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {

    }

    /**
     * opId --> 14 模具故障
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void mouldFault(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {

    }

    /**
     * opId --> 15 设备撤销报修
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void revokeDeviceReportRepair(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {

    }

    /**
     * opId --> 16 模具撤销报修
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void revokeDeviceReportRepair2(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {

    }

    /**
     * opId --> 17 设备维修
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void repairDevice(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {

    }

    /**
     * opId --> 18 设备修复
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void completeRepairDevice(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {

    }

    /**
     * opId --> 19 生产验收
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void checkProduce(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {

    }

    /**
     * opId --> 20 继续生产
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void continueProduce2(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {

    }

    /**
     * opID --> 21 中止生产
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void stopProduce(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {

    }

    /**
     * 获取模具状态机, 如果为空或者不符合指定状态则抛出异常
     *
     * @param mldDtlId 模具id
     * @param status   状态
     * @return 模具状态机
     */
    private StateMachine<MouldStatus, MouldEvents> getMouldStateMachine(Integer mldDtlId, MouldStatus status) {
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = mouldStateMachineMap.get(mldDtlId);

        if (mouldStateMachine == null ||
                StringUtils.equals(mouldStateMachine.getState().getId().toString(), status.toString())) {
            // TODO 当前状态机为空或者状态机的状态错误, 抛出异常
            log.error("模具id为#{}#的状态机为空或者状态机异常", mldDtlId);

        }
        return mouldStateMachine;

    }

    /**
     * 获取设备状态机
     *
     * @param deviceId 设备id
     * @param status   当前应该为状态
     * @return 设备状态机
     */
    private StateMachine<DeviceStatus, DeviceEvents> getDeviceMachine(Integer deviceId, DeviceStatus status) {
        StateMachine<DeviceStatus, DeviceEvents> devcStateMachine = deviceStateMachineMap.get(deviceId);
        if (devcStateMachine == null ||
                StringUtils.equals(devcStateMachine.getState().getId().toString(), status.toString())) {
            // TODO 当前状态机为空或者状态机的状态错误, 抛出异常
            log.error("设备id为#{}#的状态机为空或者状态机异常", deviceId);
        }

        return devcStateMachine;
    }

    /**
     * 封装message信息
     *
     * @param event
     * @param opId
     * @param optionId
     * @param devcId
     * @param mldDtlId
     * @param <T>
     * @return
     */
    private <T> Message<T> getMessage(T event, Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        return getMessage(event, opId, optionId, devcId, mldDtlId, null, null);
    }

    private <T> Message<T> getMessage(T event, Integer opId, Integer optionId, Integer devcId, Integer mldDtlId,
                                      String curTaskStatus, String nextTaskStatus) {
        return MessageBuilder
                .withPayload(event)
                .setHeader("opId", opId)
                .setHeader("optionId", optionId)
                .setHeader("devcId", devcId)
                .setHeader("mldDtlId", mldDtlId)
                .setHeader("curTaskStatus", curTaskStatus)
                .setHeader("nextTaskStatus", nextTaskStatus)
                .build();
    }
}