package com.hfmes.sunshine.config;

import com.hfmes.sunshine.dao.*;
import com.hfmes.sunshine.domain.*;
import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
import com.hfmes.sunshine.utils.StateMachineUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.hfmes.sunshine.utils.Constants.SD;
import static com.hfmes.sunshine.utils.Constants.SM;
import static com.hfmes.sunshine.utils.Constants.ST;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 8:42
 */
@Configuration
@Slf4j
public class BeanConfig {

    @Autowired
    private StateMachineFactory<DeviceStatus, DeviceEvents> deviceStateMachineFactory;
    @Autowired
    private StateMachineFactory<MouldStatus, MouldEvents> mouldStateMachineFactory;


    @Autowired
    private DevcDao deviceDao;

    @Autowired
    private MldDtlDao mldDtlDao;

    /* **********************************************************
     * 所有注入的bean
     ***********************************************************/

    /**
     * 存储所有设备状态机实例
     * key deviceId
     * value device statemachine instance
     *
     * @return map of device statemachine
     */
    @Bean(name = "deviceStateMachines")
    public Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStateMachines() {
        return initDeviceStateMachines();
    }

    /**
     * @return
     */
    @Bean(name = "mouldStateMachines")
    public Map<Integer, StateMachine<MouldStatus, MouldEvents>> mouldStateMachines() {
        return initMouldStateMachines();
    }



    /* **********************************************************
     * 注入bean实现过程
     ***********************************************************/


    /**
     * 初始化所有设备状态机
     *
     * @return map of device state machines
     */
    private Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> initDeviceStateMachines() {
        Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStateMachines = new ConcurrentHashMap<>();

        if (deviceStateMachineFactory == null) {
            log.warn("device state machine factory can't be null");
            return null;
        }

        List<Devc> devcs = deviceDao.findAll();
        if (devcs != null && devcs.size() != 0) {
            for (Devc devc : devcs) {
                StateMachine<DeviceStatus, DeviceEvents> stateMachine = deviceStateMachineFactory.getStateMachine(devc.getTitle());
                // 从数据库恢复到指定状态
                stateMachine.start();
                if (devc.getStatus() != null) {
                    DeviceStatus status = DeviceStatus.valueOf(devc.getStatus());
                    log.debug("# status --> {}", status);
                    StateMachineUtils.setDeviceStateMachineState(stateMachine, status);
                }
                deviceStateMachines.put(devc.getDeviceId(), stateMachine);
            }
        } else {
            log.warn("警告 --> 当前数据库中没有设备信息");
        }

        return deviceStateMachines;
    }

    /**
     * 返回模具的状态机
     *
     * @return map of mould statemachies
     */
    private Map<Integer, StateMachine<MouldStatus, MouldEvents>> initMouldStateMachines() {
        Map<Integer, StateMachine<MouldStatus, MouldEvents>> mouldStateMachines = new ConcurrentHashMap<>();

        if (mouldStateMachineFactory == null) {
            log.warn("mould state machine factory can't be null!");
            return null;
        }
        List<MldDtl> mldDtls = mldDtlDao.findAll();
        if (mldDtls != null && mldDtls.size() != 0) {
            for (MldDtl mldDtl : mldDtls) {
                StateMachine<MouldStatus, MouldEvents> stateMachine = mouldStateMachineFactory.getStateMachine(mldDtl.getCode());
                // 从数据库中恢复模具的状态
                stateMachine.start();
                if (StringUtils.isNotEmpty(mldDtl.getStatus().trim())) {
                    MouldStatus status = MouldStatus.valueOf(mldDtl.getStatus());
                    StateMachineUtils.setMouldStateMachineState(stateMachine, status);
                }

                mouldStateMachines.put(mldDtl.getMldDtlId(), stateMachine);
            }
        } else {
            log.warn("警告 --> 数据库中没有模具信息");
        }

        return mouldStateMachines;
    }
}
