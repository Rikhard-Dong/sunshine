package com.hfmes.sunshine.config;

import com.hfmes.sunshine.action.mould.*;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@EnableStateMachineFactory(name = "mouldStateMachineFactory")
@Slf4j
public class ModelStateMachineConfig extends EnumStateMachineConfigurerAdapter<MouldStatus, MouldEvents> {


    private final CompleteDemouldingAction completeDemouldingAction;
    private final CompleteMouldFillingAction completeMouldFillingAction;
    private final DemouldingAction demouldingAction;
    private final MouldCollarAction mouldCollarAction;
    @Qualifier("mouldRepairAction2")
    private final MouldRepairAction mouldRepairAction;
    private final MouldRepairComplete2SM10Action mouldRepairComplete2SM10Action;
    private final MouldRepairComplete2SM40Action mouldRepairComplete2SM40Action;
    private final MouldReportRepairAction mouldReportRepairAction;
    private final MouldReturnAction mouldReturnAction;
    private final MouldRevokeReportRepairAction mouldRevokeReportRepairAction;
    private final StartDemouldingAction startDemouldingAction;
    private final StartMouldFillingAction startMouldFillingAction;

    @Autowired
    public ModelStateMachineConfig(CompleteDemouldingAction completeDemouldingAction,
                                   CompleteMouldFillingAction completeMouldFillingAction,
                                   DemouldingAction demouldingAction,
                                   MouldCollarAction mouldCollarAction,
                                   MouldRepairAction mouldRepairAction,
                                   MouldRepairComplete2SM10Action mouldRepairComplete2SM10Action,
                                   MouldRepairComplete2SM40Action mouldRepairComplete2SM40Action,
                                   MouldReportRepairAction mouldReportRepairAction,
                                   MouldReturnAction mouldReturnAction,
                                   MouldRevokeReportRepairAction mouldRevokeReportRepairAction,
                                   StartDemouldingAction startDemouldingAction,
                                   StartMouldFillingAction startMouldFillingAction) {
        this.completeDemouldingAction = completeDemouldingAction;
        this.completeMouldFillingAction = completeMouldFillingAction;
        this.demouldingAction = demouldingAction;
        this.mouldCollarAction = mouldCollarAction;
        this.mouldRepairAction = mouldRepairAction;
        this.mouldRepairComplete2SM10Action = mouldRepairComplete2SM10Action;
        this.mouldRepairComplete2SM40Action = mouldRepairComplete2SM40Action;
        this.mouldReportRepairAction = mouldReportRepairAction;
        this.mouldReturnAction = mouldReturnAction;
        this.mouldRevokeReportRepairAction = mouldRevokeReportRepairAction;
        this.startDemouldingAction = startDemouldingAction;
        this.startMouldFillingAction = startMouldFillingAction;
    }

    @Override
    public void configure(StateMachineStateConfigurer<MouldStatus, MouldEvents> states) throws Exception {
        log.debug("初始化mouldStateMachineFactory状态");

        states.withStates()
                .initial(MouldStatus.SM00)
                .states(EnumSet.allOf(MouldStatus.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<MouldStatus, MouldEvents> transitions) throws Exception {
        log.debug("设置mouldStateMachineFactory状态事件及动作");
        transitions
                .withExternal()     // 模具领用
                .source(MouldStatus.SM00).target(MouldStatus.SM10).event(MouldEvents.MOULD_COLLAR)
                .action(mouldCollarAction)
                .and()
                .withExternal()     // 模具归还
                .source(MouldStatus.SM10).target(MouldStatus.SM00).event(MouldEvents.MOULD_RETURN)
                .action(mouldReturnAction)
                .and()
                .withExternal()     // 开始装模
                .source(MouldStatus.SM10).target(MouldStatus.SM20).event(MouldEvents.START_MOULD_FILLING)
                .action(startMouldFillingAction)
                .and()
                .withExternal()     // 装模完成
                .source(MouldStatus.SM20).target(MouldStatus.SM40).event(MouldEvents.COMPLETE_MOULD_FILLING)
                .action(completeMouldFillingAction)
                .and()
                .withExternal()     // 开始卸模
                .source(MouldStatus.SM40).target(MouldStatus.SM30).event(MouldEvents.START_DEMOULDING)
                .action(startDemouldingAction)
                .and()
                .withExternal()     // 卸模完成
                .source(MouldStatus.SM30).target(MouldStatus.SM10).event(MouldEvents.COMPLETE_DEMOULDING)
                .action(completeDemouldingAction)
                .and()
                .withExternal()     // 模具报修
                .source(MouldStatus.SM40).target(MouldStatus.SM50).event(MouldEvents.MOULD_REPORT_REPAIR)
                .action(mouldReportRepairAction)
                .and()
                .withExternal()     // 撤销报修
                .source(MouldStatus.SM50).target(MouldStatus.SM40).event(MouldEvents.MOULD_REVOKE_REPORT_REPAIR)
                .action(mouldRevokeReportRepairAction)
                .and()
                .withExternal()     // 模具维修
                .source(MouldStatus.SM50).target(MouldStatus.SM60).event(MouldEvents.MOULD_REPAIR)
                .action(mouldRepairAction)
                .and()
                .withExternal()     // 卸模/料
                .source(MouldStatus.SM60).target(MouldStatus.SM60).event(MouldEvents.DEMOULDING)
                .action(demouldingAction)
                .and()
                .withExternal()     // 模具修复, 到使用状态
                .source(MouldStatus.SM60).target(MouldStatus.SM40).event(MouldEvents.MOULD_REPAIR_COMPLETE2SM40)
                .action(mouldRepairComplete2SM40Action)
                .and()
                .withExternal()     // 模具修复, 到领用状态
                .source(MouldStatus.SM60).target(MouldStatus.SM10).event(MouldEvents.MOULD_REPAIR_COMPLETE2SM10)
                .action(mouldRepairComplete2SM10Action);
    }
}
