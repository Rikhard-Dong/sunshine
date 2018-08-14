package com.hfmes.sunshine.action.devc;

import com.hfmes.sunshine.action.BaseAction;
import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.hfmes.sunshine.utils.Constants.SD;
import static com.hfmes.sunshine.utils.Constants.ST;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 9:28
 * <p>
 * 设备撤销报修
 */
@Component
@Slf4j
public class DeviceRevokeReportRepairAction extends BaseAction implements Action<DeviceStatus, DeviceEvents> {
    @Override
    @Transactional
    public void execute(StateContext<DeviceStatus, DeviceEvents> context) {
        log.debug("设备撤销报修...");
        contextLoad(context);

        // 记录devLog
        devLog();
        // 记录状态转换
        statusDataLog(ST);
        statusDataLog(SD);

        // 更新工单状态
        updateTaskStatus();

        // 更新设备状态
        updateDevcStatus();
    }
}
