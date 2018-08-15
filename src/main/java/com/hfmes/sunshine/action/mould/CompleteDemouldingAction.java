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
 * <p>
 * 卸模完成action
 */
@Component
@Slf4j
public class CompleteDemouldingAction extends BaseAction implements Action<MouldStatus, MouldEvents> {


    @Override
    @Transactional
    public void execute(StateContext<MouldStatus, MouldEvents> context) {
        log.debug("卸模完成action....");

        contextLoad(context);

        // 记录模具操作
        mldLog("模具拆卸完成", "", "操作");

        // 更新模具状态
        updateMldStatus();

        // 设备去除模具
        removeDevcMld();

        // 记录状态转换
        statusDataLog(SM);
        resetCounts();
    }
}
