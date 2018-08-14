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
 * @date 2018/8/9 11:09
 * <p>
 * 模具修复到使用状态
 */
@Component
@Slf4j
public class MouldRepairComplete2SM40Action extends BaseAction implements Action<MouldStatus, MouldEvents> {

    @Override
    @Transactional
    public void execute(StateContext<MouldStatus, MouldEvents> context) {
        log.debug("模具修复到使用状态");

        contextLoad(context);

        // 记录日志信息
        mldLog("模具维修完成, 当前为使用状态", "", "操作");

        // 记录状态转换
        statusDataLog(SM);

        // 更新模具状态
        updateMldStatus();

        // 更新设备中的模具状态
        if (mldDtl.getMldDtlId().equals(devc.getMldDtlId())) {
            updateDevcMldDltStatus();
        } else {
            log.error("模具id#{}# 与设备#{}#中的模具id不符合", mldDtl.getMldDtlId(), devc.getDeviceId());
        }
    }
}
