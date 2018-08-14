package com.hfmes.sunshine.action.mould;

import com.hfmes.sunshine.action.BaseAction;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.hfmes.sunshine.utils.Constants.SM;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 11:05
 * 卸模开始action
 */
@Component
@Slf4j
public class StartDemouldingAction extends BaseAction implements Action<MouldStatus, MouldEvents> {

    @Override
    @Transactional
    public void execute(StateContext<MouldStatus, MouldEvents> context) {
        log.debug("卸模开始action...");

        contextLoad(context);
        mldLog("卸模操作开始", "", "操作");

        statusDataLog(SM);

        // 更新模具状态
        updateMldStatus();

        // 更新设备中的模具信息
        updateDevcMldDltStatus();
    }
}
