package com.hfmes.sunshine.action.mould;

import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 11:07
 * 模具维修action
 *
 * 区分devc action中的MouldRepairAction
 */
@Component(value = "mouldRepairAction2")
@Slf4j
public class MouldRepairAction implements Action<MouldStatus, MouldEvents> {
    @Override
    public void execute(StateContext<MouldStatus, MouldEvents> context) {
        log.debug("模具维修action");
    }
}

