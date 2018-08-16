package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.TaskDao;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.enums.DeviceStatus;
import com.hfmes.sunshine.enums.MouldStatus;
import com.hfmes.sunshine.enums.TaskStatus;
import com.hfmes.sunshine.service.ConditionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Qualifier("deviceTasks")
    private Map<Integer, List<Task>> deviceTaskMap;

    @Autowired
    private TaskDao taskDao;

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
        log.debug("devc --> {}", devc);
        return devc != null && StringUtils.isNotEmpty(devc.getMldStatus());
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
        return devc.getMldStatus() != null && MouldStatus.valueOf(devc.getMldStatus()) == MouldStatus.SM40;
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

        log.debug("task is {}", task);

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

        log.warn("当前生产数量为{}, 设置数量为{}", task.getProcNum(), task.getSetNum());

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

    /**
     * 是否拥有下一个订单
     *
     * @param devcId 设备Id
     * @return
     */
    @Override
    public Boolean hasNextTask(Integer devcId) {

        Devc devc = devcMap.get(devcId);
        if (devc == null) {
            // TODO 内存数据中不存在当前设备
            log.warn("设备不存在...");
            return false;
        }


        boolean flag = false;
        boolean isGet = false;
        int idx = 0;
        List<Task> tasksTemp = taskDao.findByStatusIsST00AndDevTask();
        deviceTaskMap.put(devcId, tasksTemp);
        for (int i = 0; i < tasksTemp.size(); i++) {
            Task tmp = tasksTemp.get(i);
            if (tmp.getTaskId().equals(devc.getTaskId())) {
                flag = true;
                idx = i;
                continue;
            }
            if (flag) {
                if ((devc.getMldDtlId() == null || tmp.getMldDtlId().equals(devc.getMldDtlId())) && StringUtils.equals(tmp.getStatus(), TaskStatus.ST00.toString())) {
                    isGet = true;
                    break;
                }
            }
        }
        if (!isGet) {
            for (int i = 0; i < idx; i++) {
                Task tmp = tasksTemp.get(i);
                if (StringUtils.equals(tmp.getStatus(), TaskStatus.ST00.toString()) && (devc.getMldDtlId() == null || tmp.getMldDtlId().equals(devc.getMldDtlId()))) {
                    isGet = true;
                    break;
                }
            }
        }

        log.info("是否拥有下一个订单 --> {}", isGet);
        return isGet;
    }

    @Override
    public Boolean isMouldSame(Integer devcId) {
        Devc devc = devcMap.get(devcId);
        if (devc == null) {
            // TODO 内存数据中不存在当前设备
            log.warn("设备不存在...");
            return false;
        }

        return devc.getMldDtlId() != null && devc.getMldDtlId().equals(devc.getTask().getMldDtlId());
    }
}
