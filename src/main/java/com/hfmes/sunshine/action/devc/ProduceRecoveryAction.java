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
 * @date 2018/8/9 9:25
 * <p>
 * 恢复生产
 */
@Component
@Slf4j
public class ProduceRecoveryAction extends BaseAction implements Action<DeviceStatus, DeviceEvents> {
    @Override
    @Transactional
    public void execute(StateContext<DeviceStatus, DeviceEvents> context) {
        log.debug("恢复生产...");
        contextLoad(context);

        devLog();
        statusDataLog(SD);
        statusDataLog(ST);

        updateTaskStatus();
        updateDevcStatus();
    }
}
