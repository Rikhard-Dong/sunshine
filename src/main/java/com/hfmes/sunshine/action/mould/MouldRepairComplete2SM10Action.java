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
 * 模具修复到领用状态
 */
@Component
@Slf4j
public class MouldRepairComplete2SM10Action extends BaseAction implements Action<MouldStatus, MouldEvents> {

    @Override
    @Transactional
    public void execute(StateContext<MouldStatus, MouldEvents> context) {
        log.debug("模具修复到领用状态....");
        contextLoad(context);
        updateNum();

        // 记录操作
        mldLog("模具维修完成, 当前为领用状态", "", "操作");

        // 记录转换
        statusDataLog(SM);

        // 更新模具状态
        updateMldStatus();

        endMldRpr();
    }
}

