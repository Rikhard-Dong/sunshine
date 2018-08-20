package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.cache.CountNumsCache;
import com.hfmes.sunshine.cache.DevcCache;
import com.hfmes.sunshine.cache.TasksCache;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.enums.DeviceStatus;
import com.hfmes.sunshine.enums.MouldStatus;
import com.hfmes.sunshine.enums.TaskStatus;
import com.hfmes.sunshine.service.CountNumService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CountNumServiceImpl implements CountNumService {
    @Override
    public boolean updateLocalToServerCount(String devcId, String count) {

        Devc devc = DevcCache.get(Integer.parseInt(devcId));
        if (devc == null) {
            log.error("更新服务器计数报错! devcId#{}#对应设备为null", devcId);
            return false;
        }
        Task task = TasksCache.get(devc.getTaskId());

        if (task == null) {
            log.error("更新服务器计数报错! taskId#{}#对应工单为null", devc.getTaskId());
            return false;
        } else {
            int num = CountNumsCache.get(Integer.parseInt(devcId));
            log.info("num --> {}, count --> {}", num, count);
            num = num + Integer.parseInt(count);


            if (devc.getTask() != null && devc.getTaskId() != 0) {
                if (StringUtils.equals(devc.getMldStatus(), MouldStatus.SM40.toString())
                        && StringUtils.equals(devc.getStatus(), DeviceStatus.SD10.toString())
                        && StringUtils.equals(task.getStatus(), TaskStatus.ST10.toString())) {
                    devc.getTask().setProcNum(devc.getTask().getProcNum() + Integer.parseInt(count));
                } else {
                    devc.getTask().setTestNum(devc.getTask().getTestNum() + Integer.parseInt(count));
                }
            } else {
                log.warn("当前设备id#{}#没有工单", devcId);
            }
            try {
                log.info("device id -> {}, 生产数量 {}", devc.getDeviceId(), devc.getTask().getProcNum());
//                devcs.put(devc.getDeviceId(), devc);
                CountNumsCache.put(devc.getDeviceId(), num);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
    }
}
