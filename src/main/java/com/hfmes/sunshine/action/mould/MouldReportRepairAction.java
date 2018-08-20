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
 * @date 2018/8/9 11:06
 * 模具报修action
 */
@Component
@Slf4j
public class MouldReportRepairAction extends BaseAction implements Action<MouldStatus, MouldEvents> {

    @Override
    @Transactional
    public void execute(StateContext<MouldStatus, MouldEvents> context) {
        log.debug("模具报修action");
        contextLoad(context);
        updateNum();
        // 模具mldLog记录
        mldLog("模具报修", "", "操作");


        // 更新模具状态
        updateMldStatus();

        // 更新设备中模具状态
        updateDevcMldDltStatus();

        addMldRpr();

        // 记录状态转换
        statusDataLog(SM);
        resetCounts();
    }
}
