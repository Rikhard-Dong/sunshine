package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.MldDtl;
import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
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
     * |---  2. 更新模具状态信息, 从领用状态(SM10)变为装模状态(SM20)
     * |---  3. 为设备绑定模具id, 设置模具状态
     * |---  4. 记录MtlLog日志(对应action中)
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
     * |--- 2. 更新对应模具状态以及设备中模具对应的状态
     * |--- 3. 记录对应mtlLog操作日志(对应action中)
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

        }
        return mouldStateMachine;

    }

    private <T> Message<T> getMessage(T event, Integer opId, Integer optionId, Integer devcId, Integer mldDtlId) {
        return MessageBuilder
                .withPayload(event)
                .setHeader("opId", opId)
                .setHeader("optionId", optionId)
                .setHeader("devcId", devcId)
                .setHeader("mldDtlId", mldDtlId)
                .build();
    }
}
