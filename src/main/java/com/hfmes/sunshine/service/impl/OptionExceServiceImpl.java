package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.TaskDao;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.MldDtl;
import com.hfmes.sunshine.domain.StatusData;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.enums.*;
import com.hfmes.sunshine.service.LogService;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hfmes.sunshine.utils.Constants.BTN_EVENT_TYPE;
import static com.hfmes.sunshine.utils.Constants.ST;

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
    private final Map<Integer, List<Task>> devcTasks;
    private final Map<Integer, Task> tasks;


    private final LogService logService;

    private final TaskDao taskDao;

    @Autowired
    public OptionExceServiceImpl(@Qualifier("deviceStateMachines")
                                         Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStateMachineMap,
                                 @Qualifier("mouldStateMachines")
                                         Map<Integer, StateMachine<MouldStatus, MouldEvents>> mouldStateMachineMap,
                                 Map<Integer, Devc> descMap,
                                 Map<Integer, MldDtl> mldDtlMap,
                                 TaskDao taskDao,
                                 LogService logService,
                                 @Qualifier("deviceTasks") Map<Integer, List<Task>> devcTasks,
                                 @Qualifier("tasks") Map<Integer, Task> tasks) {
        this.deviceStateMachineMap = deviceStateMachineMap;
        this.mouldStateMachineMap = mouldStateMachineMap;
        this.devcMap = descMap;
        this.mldDtlMap = mldDtlMap;
        this.logService = logService;
        this.taskDao = taskDao;
        this.devcTasks = devcTasks;
        this.tasks = tasks;
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
     * |--- 4. 更新工单状态, 分配ST00 -> 生产ST10从tasks和deviceTasks中移除该工单
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void startProduce(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD00);
        Message<DeviceEvents> message = getMessage(DeviceEvents.PRODUCE_START, opId, optionId, devcId, mldDtlId,
                TaskStatus.ST00.toString(), TaskStatus.ST10.toString());
        deviceStateMachine.sendEvent(message);
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
        Message<DeviceEvents> message = getMessage(DeviceEvents.PRODUCE_RECOVERY, opId, optionId, devcId, mldDtlId,
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
//    @Transactional
    public void completeProduce(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        log.warn("执行生产完成操作, 时间-->{}", new Date());


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
     * |--- 3. 更新工单状态 ST10执行 -> ST20暂停 并记录状态转换
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
     * 执行操作
     * |--- 1. 设备状态从运行SD10 -> 故障SD20, 记录状态转换过程
     * |--- 2. 纪录devLog日志
     * |--- 3. 更新工单状态 ST10执行 -> ST20暂停 并记录状态转换
     * |--- 4. 更新设备状态, 工单信息
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void deviceFault(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD10);
        Message<DeviceEvents> message = getMessage(DeviceEvents.DEVICE_REPORT_REPAIR, opId, optionId, devcId, mldDtlId,
                TaskStatus.ST10.toString(), TaskStatus.ST20.toString());
        deviceStateMachine.sendEvent(message);
    }

    /**
     * opId --> 14 模具故障
     * 执行操作
     * |--- 1. 设备状态从运行SD10 -> 待机SD00, 记录状态转换
     * |--- 2. 记录devLog
     * |--- 3. 模具状态从使用SM40 -> 故障SM50, 记录状态转换
     * |--- 4. 记录mldLog
     * |--- 5. 更新工单状态 ST10执行 -> ST20暂停 并记录状态转换
     * |--- 6. 更新设备状态, 工单信息
     * |--- 7. 更新模具状态信息
     * |--- 8. 更新设备模具状态
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
//    @Transactional
    public void mouldFault(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = getMouldStateMachine(mldDtlId, MouldStatus.SM40);
        Message<MouldEvents> message1 = getMessage(MouldEvents.MOULD_REPORT_REPAIR, opId, optionId, devcId, mldDtlId);
        mouldStateMachine.sendEvent(message1);

        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD10);
        Message<DeviceEvents> message2 = getMessage(DeviceEvents.PRODUCE_SUSPEND, opId, optionId, devcId, mldDtlId,
                TaskStatus.ST10.toString(), TaskStatus.ST20.toString());
        deviceStateMachine.sendEvent(message2);
    }

    /**
     * opId --> 15 设备撤销报修
     * 执行操作
     * |--- 1. 设备状态故障SD20 -> 运行SD10 记录状态转换
     * |--- 2. 记录devLog
     * |--- 3. 更新工单状态 暂停ST20 -> 执行ST10 记录状态转换
     * |--- 4. 更新设备状态
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void revokeDeviceReportRepair(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD20);
        Message<DeviceEvents> message = getMessage(DeviceEvents.DEVICE_REVOKE_REPORT_REPAIR, opId, optionId, devcId, mldDtlId,
                TaskStatus.ST20.toString(), TaskStatus.ST10.toString());
        deviceStateMachine.sendEvent(message);
    }

    /**
     * opId --> 16 模具撤销报修
     * 执行操作
     * |--- 1. 更新设备状态待机SD00->运行SD10, 并记录状态转换
     * |--- 2. 更新工单状态暂停ST20->执行ST10, 并记录状态转换
     * |--- 3. 更新模具状态故障SM50->使用SM40, 并记录状态转换
     * |--- 4. 记录devLog
     * |--- 5. 记录mldLog
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void revokeDeviceReportRepair2(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<MouldStatus, MouldEvents> mouldStateMachine = getMouldStateMachine(mldDtlId, MouldStatus.SM50);
        Message<MouldEvents> message1 = getMessage(MouldEvents.MOULD_REVOKE_REPORT_REPAIR, opId, optionId, devcId, mldDtlId);
        mouldStateMachine.sendEvent(message1);

        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD00);
        Message<DeviceEvents> message2 = getMessage(DeviceEvents.PRODUCE_RECOVERY, opId, optionId, devcId, mldDtlId,
                TaskStatus.ST20.toString(), TaskStatus.ST10.toString());
        deviceStateMachine.sendEvent(message2);
    }

    /**
     * opId --> 17 设备维修
     * 执行操作
     * |--- 1. 更新设备状态故障SD20 -> 检修SD30, 并记录状态转换
     * |--- 2. 记录devLog
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void repairDevice(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD20);
        Message<DeviceEvents> message = getMessage(DeviceEvents.DEVICE_REPAIR, opId, optionId, devcId, mldDtlId);
        deviceStateMachine.sendEvent(message);
    }

    /**
     * opId --> 18 设备修复
     * 执行操作
     * |--- 1. 更新设备状态 检修SD30 --> 待机SD00 并记录状态转换
     * |--- 2. 记录devLog
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void completeRepairDevice(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD30);
        Message<DeviceEvents> message = getMessage(DeviceEvents.DEVICE_REPAIR_COMPLETE, opId, optionId, devcId, mldDtlId);
        deviceStateMachine.sendEvent(message);
    }

    /**
     * opId --> 19 生产验收
     * 执行操作
     * |--- 1 工单状态从待验收ST30 -> 完成ST40
     * |--- 2 选择下一单
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void checkProduce(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD00);
        Message<DeviceEvents> message = getMessage(DeviceEvents.PRODUCE_CHECK_AND_ACCEPT, opId, optionId, devcId, mldDtlId,
                TaskStatus.ST30.toString(), TaskStatus.ST40.toString());
        deviceStateMachine.sendEvent(message);
    }

    /**
     * opId --> 20 继续生产
     * 执行操作
     * |--- 1. 工单状态待验收ST30 -> 暂停ST20, 并记录状态转换
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void continueProduce2(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD00);
        Message<DeviceEvents> message = getMessage(DeviceEvents.PRODUCE_CONTINUE, opId, optionId, devcId, mldDtlId,
                TaskStatus.ST30.toString(), TaskStatus.ST20.toString());
        deviceStateMachine.sendEvent(message);
    }


    /**
     * opID --> 21 中止生产
     * 执行操作
     * |--- 1. 更新工单状态为暂停ST20 -> 待验收ST30
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void stopProduce(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        Devc devc = devcMap.get(devcId);
        if (devc == null) {
            log.error("stop produce 停止生产出错, 没有设备id为#{}#的对象", devcId);
            return;
        }
        Task task = devc.getTask();
        task.setStatus(TaskStatus.ST30.toString());
        taskDao.updateStatus(task.getTaskId(), task.getStatus());
        devc.setTask(task);
        devcMap.put(devcId, devc);

        // 如果生产数量没有达到则继续生产
        if (task.getProcNum() < task.getSetNum()) {
            try {
                Task newTask = (Task) task.clone();
                newTask.setStartTime(null);
                newTask.setEndTime(null);
                newTask.setTestNum(0);
                newTask.setProcNum(0);
                newTask.setSetNum(task.getSetNum() - task.getProcNum());
                newTask.setMldStartTime(new Date());
                newTask.setMldEndTime(new Date());
                newTask.setTaskId(null);
                newTask.setStatus(TaskStatus.ST00.toString());
                newTask.setMldStartTime(null);
                newTask.setMldEndTime(null);
                newTask.setMldPlanStart(null);
                newTask.setMldPlanEnd(null);
                taskDao.insertOne(newTask);

                log.info("产生新单 --> {}", newTask);

                // 将产生的新的工单添加到内存数据中
                tasks.put(newTask.getTaskId(), task);
                List<Task> temp = devcTasks.get(devcId);
                temp.add(newTask);
                devcTasks.put(devcId, temp);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        // 记录状态转换
        StatusData statusData = new StatusData();
        statusData.setCurStatus(TaskStatus.ST20.toString());
        statusData.setNextStatus(TaskStatus.ST30.toString());

        statusData.setOpId(opId);
        statusData.setDevId(devcId);
        statusData.setMldId(mldDtlId);
        statusData.setTaskId(task.getTaskId());
        statusData.setEventType(BTN_EVENT_TYPE);
        statusData.setEventName(String.valueOf(optionId));
        statusData.setStatusTypeId(ST);

        logService.statusDataLog(statusData);
    }

    /**
     * opId -> 22 冲床工选择下一单
     * workType 0  冲床工
     * wokrType 1  生产管理
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void nextTaskForPunch(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD00);
        Map<String, Object> params = new HashMap<>();
        params.put("opId", opId);
        params.put("optionId", optionId);
        params.put("devcId", devcId);
        params.put("mldDtlId", mldDtlId);
        params.put("workerType", 0);
        deviceStateMachine.sendEvent(getMessage(DeviceEvents.PRODUCE_NEXT_ORDER, params));
    }

    /**
     * opId -> 23 生产管理选择下一单
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    @Override
    @Transactional
    public void nextTaskForPBCB(Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        StateMachine<DeviceStatus, DeviceEvents> deviceStateMachine = getDeviceMachine(devcId, DeviceStatus.SD00);
        Map<String, Object> params = new HashMap<>();
        params.put("opId", opId);
        params.put("optionId", optionId);
        params.put("devcId", devcId);
        params.put("mldDtlId", mldDtlId);
        params.put("workerType", 1);
        deviceStateMachine.sendEvent(getMessage(DeviceEvents.PRODUCE_NEXT_ORDER, params));
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
                !StringUtils.equals(mouldStateMachine.getState().getId().toString(), status.toString())) {

            log.error("模具id为#{}#的状态机为空或者状态机异常, machine --> {}", mldDtlId, mouldStateMachine);
            if (mouldStateMachine != null) {
                log.error("当前状态机状态为{}, 需要状态为{}", mouldStateMachine.getState().getId().toString(), status.toString());
            }
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
                !StringUtils.equals(devcStateMachine.getState().getId().toString(), status.toString())) {
            // TODO 当前状态机为空或者状态机的状态错误, 抛出异常
            log.error("设备id为#{}#的状态机为空或者状态机异常", deviceId);
            if (devcStateMachine != null) {
                log.error("当前状态机状态为{}, 需要状态为{}", devcStateMachine.getState().getId().toString(), status.toString());
            }
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

    /**
     * 封装message信息
     *
     * @param event
     * @param opId
     * @param optionId
     * @param devcId
     * @param mldDtlId
     * @param curTaskStatus
     * @param nextTaskStatus
     * @param <T>
     * @return
     */
    private <T> Message<T> getMessage(T event, Integer opId, Integer optionId, Integer devcId, Integer mldDtlId,
                                      String curTaskStatus, String nextTaskStatus) {

        Map<String, Object> params = new HashMap<>();
        params.put("opId", opId);
        params.put("optionId", optionId);
        params.put("devcId", devcId);
        params.put("mldDtlId", mldDtlId);
        params.put("curTaskStatus", curTaskStatus);
        params.put("nextTaskStatus", nextTaskStatus);

        return getMessage(event, params);


    }

    /**
     * 封装message信息
     *
     * @param event
     * @param params
     * @param <T>
     * @return
     */
    private <T> Message<T> getMessage(T event, Map<String, Object> params) {
        MessageBuilder<T> builder = MessageBuilder.withPayload(event);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.setHeader(entry.getKey(), entry.getValue());
        }

        return builder.build();
    }
}
