package com.hfmes.sunshine.action.mould;

import com.hfmes.sunshine.action.BaseAction;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 11:05
 * 卸模开始action
 */
@Component
@Slf4j
public class StartDemouldingAction extends BaseAction implements Action<MouldStatus, MouldEvents> {
    @Override
    public void execute(StateContext<MouldStatus, MouldEvents> context) {
        log.debug("撤销模具报修action...");
    }
}
