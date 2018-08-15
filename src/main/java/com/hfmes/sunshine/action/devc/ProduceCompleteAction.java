package com.hfmes.sunshine.action.devc;

import com.hfmes.sunshine.action.BaseAction;
import com.hfmes.sunshine.dao.PlanDtlDao;
import com.hfmes.sunshine.domain.PlanDtl;
import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.hfmes.sunshine.utils.Constants.SD;
import static com.hfmes.sunshine.utils.Constants.ST;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 9:26
 * <p>
 * 生产完成action
 */
@Component
@Slf4j
public class ProduceCompleteAction extends BaseAction implements Action<DeviceStatus, DeviceEvents> {

    @Autowired
    private PlanDtlDao planDtlDao;

    @Override
    @Transactional
    public void execute(StateContext<DeviceStatus, DeviceEvents> context) {
        log.debug("生产完成...");
        contextLoad(context);
        updateNum();

        // 记录devLog日志
        devLog("生产完成", "", "操作");


        // 更新工单状态
        updateTaskStatus();

        // 更新设备状态
        updateDevcStatus();

        taskDao.updateEndTime(taskId, new Date());

        // 更新PlanDtl
        updatePlanDtl();


        // 记录状态转换
        statusDataLog(SD);
        statusDataLog(ST);
        resetCounts();

    }
}
