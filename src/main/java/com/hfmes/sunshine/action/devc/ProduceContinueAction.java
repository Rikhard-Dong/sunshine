package com.hfmes.sunshine.action.devc;

import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 9:22
 *
 * 继续生产action
 */
@Component
@Slf4j
public class ProduceContinueAction  implements Action<DeviceStatus, DeviceEvents> {
    @Override
    public void execute(StateContext<DeviceStatus, DeviceEvents> context) {
        log.debug("继续生产...");
    }
}
