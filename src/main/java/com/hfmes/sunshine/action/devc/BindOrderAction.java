package com.hfmes.sunshine.action.devc;

import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 8:59
 * <p>
 * 绑定订单action
 */
@Component
@Slf4j
public class BindOrderAction implements Action<DeviceStatus, DeviceEvents> {

    @Override
    public void execute(StateContext<DeviceStatus, DeviceEvents> context) {
        log.debug("绑定订单..");
    }
}
