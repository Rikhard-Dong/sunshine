package com.hfmes.sunshine.action.mould;

import com.hfmes.sunshine.action.BaseAction;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import static com.hfmes.sunshine.utils.Constants.SM;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 11:05
 * 装模完成action
 */
@Component
@Slf4j
public class CompleteMouldFillingAction extends BaseAction implements Action<MouldStatus, MouldEvents> {
    @Override
    public void execute(StateContext<MouldStatus, MouldEvents> context) {
        log.debug("装模完成action....");
        contextLoad(context);

        // 插入模具日志信息
        mldLog();

        // 记录状态转换
        statusDataLog(SM);

        // 更新模具状态机设备中模具的状态信息
        mldDtl.setStatus(nextStatus);
        mldDtlDao.updateStatus(mldDtlId, nextStatus);

        // 更新设备信息
        devc.setMldDtlId(mldDtlId);
        devc.setMldDtl(mldDtl);
        devc.setMldStatus(mldDtl.getStatus());

        devcDao.updateMldDtlIdAndMldStatus(devcId, mldDtlId, nextStatus);
    }
}

