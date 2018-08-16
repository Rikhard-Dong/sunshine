package com.hfmes.sunshine.action.mould;

import com.hfmes.sunshine.action.BaseAction;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 11:08
 * <p>
 * 卸模/料 action
 */
@Component
@Slf4j
public class DemouldingAction extends BaseAction implements Action<MouldStatus, MouldEvents> {

    @Override
    @Transactional
    public void execute(StateContext<MouldStatus, MouldEvents> context) {
        log.debug("卸模/料 action...");

        contextLoad(context);
        updateNum();

        // 记录mldLog日志信息
        mldLog("卸模/料", "", "操作");

        // 将模具置空
        removeDevcMld();
    }
}

