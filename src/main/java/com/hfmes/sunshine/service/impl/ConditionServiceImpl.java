package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.enums.DeviceStatus;
import com.hfmes.sunshine.enums.MouldStatus;
import com.hfmes.sunshine.service.ConditionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 9:54
 * <p>
 * 判断条件结果
 */
@Service
@Slf4j
public class ConditionServiceImpl implements ConditionService {


    private final Map<Integer, Devc> devcMap;

    @Autowired
    public ConditionServiceImpl(@Qualifier("devcs") Map<Integer, Devc> devcMap) {
        this.devcMap = devcMap;
    }

    /**
     * @param deviceId 设备id
     * @return
     */
    @Override
    public Boolean hasMould(Integer deviceId) {
        Devc devc = devcMap.get(deviceId);
        return devc != null && devc.getMldStatus() != null;
    }

    /**
     * @param deviceId 设备Id
     * @return
     */
    @Override
    public Boolean mldOpLegal(Integer deviceId, Integer personId) {
        Devc devc = devcMap.get(deviceId);
        if (devc == null) {
            // TODO 内存数据中不存在当前设备
            log.warn("设备不存在...");
            return false;
        }
        Task task = devc.getTask();


        return task != null && (task.getMldOpId() == 0 || task.getMldOpId().equals(personId));
    }

    /**
     * @param deviceId 设备id
     * @return 运行 true, 否则false
     */
    @Override
    public Boolean isDevcRun(Integer deviceId) {
        Devc devc = devcMap.get(deviceId);
        if (devc == null) {
            // TODO 内存数据中不存在当前设备
            log.warn("设备不存在...");
            return false;
        }

        return devc.getStatus() != null && DeviceStatus.valueOf(devc.getStatus()) == DeviceStatus.SD10;
    }

    /**
     * @param deviceId 设备id
     * @return 使用 True
     */
    @Override
    public Boolean isMouldUse(Integer deviceId) {
        Devc devc = devcMap.get(deviceId);
        if (devc == null) {
            // TODO 内存数据中不存在当前设备
            log.warn("设备不存在...");
            return false;
        }
        return devc.getMldStatus() != null && MouldStatus.valueOf(devc.getStatus()) == MouldStatus.SM40;
    }

    /**
     * @param deviceId 设备id
     * @param personId 刷卡人id
     * @return
     */
    @Override
    public Boolean devOpLegal(Integer deviceId, Integer personId) {
        Devc devc = devcMap.get(deviceId);
        if (devc == null) {
            // TODO 内存数据中不存在当前设备
            log.warn("设备不存在...");
            return false;
        }
        Task task = devc.getTask();


        return task != null && (task.getDevOpId() == 0 || task.getDevOpId().equals(personId));
    }

    /**
     * @param deviceId 设备id
     * @param personId 刷卡者id
     * @return
     */
    @Override
    public Boolean isTaskDevOpEqualsCurPerson(Integer deviceId, Integer personId) {
        Devc devc = devcMap.get(deviceId);
        if (devc == null) {
            // TODO 内存数据中不存在当前设备
            log.warn("设备不存在...");
            return false;
        }

        Task task = devc.getTask();

        return task.getDevOp() != null && task.getDevOp().getPersonId().equals(personId);
    }

    @Override
    public Boolean procNumAchieveSetNum(Integer deviceId) {
        Devc devc = devcMap.get(deviceId);
        if (devc == null) {
            // TODO 内存数据中不存在当前设备
            log.warn("设备不存在...");
            return false;
        }
        Task task = devc.getTask();

        return task != null && task.getProcNum() != null && task.getSetNum() != null
                && task.getProcNum() >= task.getSetNum();
    }

    @Override
    public Boolean procNumLessThanSetNum(Integer devcId) {
        Devc devc = devcMap.get(devcId);
        if (devc == null) {
            // TODO 内存数据中不存在当前设备
            log.warn("设备不存在...");
            return false;
        }


        Task task = devc.getTask();

        return task != null && task.getProcNum() != null && task.getSetNum() != null
                && task.getProcNum() < task.getSetNum();
    }
}