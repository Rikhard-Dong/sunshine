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
 * @date 2018/8/9 11:07
 * 模具维修action
 * <p>
 * 区分devc action中的MouldRepairAction
 */
@Component(value = "mouldRepairAction2")
@Slf4j
public class MouldRepairAction extends BaseAction implements Action<MouldStatus, MouldEvents> {


    @Override
    @Transactional
    public void execute(StateContext<MouldStatus, MouldEvents> context) {
        log.debug("模具维修action....");
        contextLoad(context);

        // 记录模具操作日志
        mldLog("模具维修", "", "操作");

        // 更新模具状态
        updateMldStatus();

        // 如果设备有该模具
        if (devc.getMldDtl() != null && devc.getMldDtl().getMldDtlId().equals(mldDtl.getMldDtlId())) {
            // 更新模具状态信息
            updateDevcMldDltStatus();
        } else {
            log.info("device id #{}#中没有模具", devcId);
        }

        startMldRpr();
        // 记录状态转换操作
        statusDataLog(SM);
        resetCounts();
    }
}

