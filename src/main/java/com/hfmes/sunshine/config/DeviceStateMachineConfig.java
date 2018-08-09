package com.hfmes.sunshine.config;

import com.hfmes.sunshine.action.devc.*;
import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
@EnableStateMachineFactory(name = "deviceStateMachineFactory")
@Slf4j
public class DeviceStateMachineConfig extends EnumStateMachineConfigurerAdapter<DeviceStatus, DeviceEvents> {

    private final BindOrderAction bindOrderAction;
    private final DeviceRepairAction deviceRepairAction;
    private final DeviceRepairCompleteAction deviceRepairCompleteAction;
    private final DeviceReportRePairAction deviceReportRePairAction;
    private final DeviceRevokeReportRepairAction deviceRevokeReportRepairAction;
    @Qualifier("mouldRepairAction1")
    private final MouldRepairAction mouldRepairAction;
    private final MouldRepairCompleteAction mouldRepairCompleteAction;
    private final ProduceCheckAndAcceptAction produceCheckAndAcceptAction;
    private final ProduceCompleteAction produceCompleteAction;
    private final ProduceContinueAction produceContinueAction;
    private final ProduceNextOrderAction produceNextOrderAction;
    private final ProduceRecoveryAction produceRecoveryAction;
    private final ProduceStartAction produceStartAction;
    private final ProduceSuspendAction produceSuspendAction;
    private final StopAndNewOrderAction stopAndNewOrderAction;

    public DeviceStateMachineConfig(BindOrderAction bindOrderAction,
                                    DeviceRepairAction deviceRepairAction,
                                    DeviceRepairCompleteAction deviceRepairCompleteAction,
                                    DeviceReportRePairAction deviceReportRePairAction,
                                    DeviceRevokeReportRepairAction deviceRevokeReportRepairAction,
                                    MouldRepairAction modelRepairAction,
                                    MouldRepairCompleteAction modelRepairCompleteAction,
                                    ProduceCheckAndAcceptAction produceCheckAndAcceptAction,
                                    ProduceCompleteAction produceCompleteAction,
                                    ProduceContinueAction produceContinueAction,
                                    ProduceNextOrderAction produceNextOrderAction,
                                    ProduceRecoveryAction produceRecoveryAction,
                                    ProduceStartAction produceStartAction,
                                    ProduceSuspendAction produceSuspendAction,
                                    StopAndNewOrderAction stopAndNewOrderAction) {
        this.bindOrderAction = bindOrderAction;
        this.deviceRepairAction = deviceRepairAction;
        this.deviceRepairCompleteAction = deviceRepairCompleteAction;
        this.deviceReportRePairAction = deviceReportRePairAction;
        this.deviceRevokeReportRepairAction = deviceRevokeReportRepairAction;
        this.mouldRepairAction = modelRepairAction;
        this.mouldRepairCompleteAction = modelRepairCompleteAction;
        this.produceCheckAndAcceptAction = produceCheckAndAcceptAction;
        this.produceCompleteAction = produceCompleteAction;
        this.produceContinueAction = produceContinueAction;
        this.produceNextOrderAction = produceNextOrderAction;
        this.produceRecoveryAction = produceRecoveryAction;
        this.produceStartAction = produceStartAction;
        this.produceSuspendAction = produceSuspendAction;
        this.stopAndNewOrderAction = stopAndNewOrderAction;
    }


    @Override
    public void configure(StateMachineStateConfigurer<DeviceStatus, DeviceEvents> states) throws Exception {
        log.debug("初始化DeviceStateMachine状态");

        states.withStates()
                .initial(DeviceStatus.SD00)
                .states(EnumSet.allOf(DeviceStatus.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<DeviceStatus, DeviceEvents> transitions) throws Exception {
        log.debug("设置DeviceStateMachine状态事件及动作");

        transitions
                .withExternal()     // 绑定订单
                .source(DeviceStatus.SD00).target(DeviceStatus.SD00).event(DeviceEvents.BIND_ORDER)
                .action(bindOrderAction)
                .and()
                .withExternal()     // 生产下一单
                .source(DeviceStatus.SD00).target(DeviceStatus.SD00).event(DeviceEvents.PRODUCE_NEXT_ORDER)
                .action(produceNextOrderAction)
                .and()
                .withExternal()     // 生产验收
                .source(DeviceStatus.SD00).target(DeviceStatus.SD00).event(DeviceEvents.PRODUCE_CHECK_AND_ACCEPT)
                .action(produceCheckAndAcceptAction)
                .and()
                .withExternal()     // 继续生产
                .source(DeviceStatus.SD00).target(DeviceStatus.SD00).event(DeviceEvents.PRODUCE_CONTINUE)
                .action(produceContinueAction)
                .and()
                .withExternal()     // 中止并新单
                .source(DeviceStatus.SD00).target(DeviceStatus.SD00).event(DeviceEvents.STOP_AND_NEW_ORDER)
                .action(stopAndNewOrderAction)
                .and()
                .withExternal()     // 开始生产
                .source(DeviceStatus.SD00).target(DeviceStatus.SD10).event(DeviceEvents.PRODUCE_START)
                .action(produceStartAction)
                .and()
                .withExternal()     // 恢复生产
                .source(DeviceStatus.SD00).target(DeviceStatus.SD10).event(DeviceEvents.PRODUCE_RECOVERY)
                .action(produceRecoveryAction)
                .and()
                .withExternal()     // 生产完成
                .source(DeviceStatus.SD10).target(DeviceStatus.SD00).event(DeviceEvents.PRODUCE_COMPLETE)
                .action(produceCompleteAction)
                .and()
                .withExternal()     // 暂停生产
                .source(DeviceStatus.SD10).target(DeviceStatus.SD00).event(DeviceEvents.PRODUCE_SUSPEND)
                .action(produceSuspendAction)
                .and()
                .withExternal()     // 故障报修
                .source(DeviceStatus.SD10).target(DeviceStatus.SD20).event(DeviceEvents.DEVICE_REPORT_REPAIR)
                .action(deviceReportRePairAction)
                .and()
                .withExternal()     // 撤销报修
                .source(DeviceStatus.SD20).target(DeviceStatus.SD10).event(DeviceEvents.DEVICE_REVOKE_REPORT_REPAIR)
                .action(deviceRevokeReportRepairAction)
                .and()
                .withExternal()     // 设备维修
                .source(DeviceStatus.SD20).target(DeviceStatus.SD30).event(DeviceEvents.DEVICE_REPAIR)
                .action(deviceRepairAction)
                .and()
                .withExternal()     // 模具维修
                .source(DeviceStatus.SD20).target(DeviceStatus.SD30).event(DeviceEvents.MOULD_REPAIR)
                .action(mouldRepairAction)
                .and()
                .withExternal()     // 设备维修完成
                .source(DeviceStatus.SD30).target(DeviceStatus.SD00).event(DeviceEvents.DEVICE_REPAIR_COMPLETE)
                .action(deviceRepairCompleteAction)
                .and()
                .withExternal()     // 模具维修完成
                .source(DeviceStatus.SD30).target(DeviceStatus.SD00).event(DeviceEvents.MOULD_REPAIR_COMPLETE)
                .action(mouldRepairCompleteAction);


    }
}
