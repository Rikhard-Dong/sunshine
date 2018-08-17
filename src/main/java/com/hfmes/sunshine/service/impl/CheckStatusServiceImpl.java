package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.cache.DevcCache;
import com.hfmes.sunshine.cache.MldDtlsCache;
import com.hfmes.sunshine.cache.TasksCache;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.MldDtl;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.service.CheckStatusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 15:58
 * <p>
 * 检查咋黄台同步情况
 */
@Service
public class CheckStatusServiceImpl implements CheckStatusService {

    /**
     * @param deviceId   设备id
     * @param devcStatus 下位机上传状态
     * @return
     */
    @Override
    public Boolean checkDevcStatus(Integer deviceId, String devcStatus) {
        Devc devc = DevcCache.get(deviceId);

        return devc != null && StringUtils.equals(devcStatus, devc.getStatus());
    }

    /**
     * @param mldId     模具id
     * @param mldStatus 模具状态
     * @return
     */
    @Override
    public Boolean checkMldStatus(Integer mldId, String mldStatus) {
        MldDtl mldDtl = MldDtlsCache.get(mldId);
        return mldDtl != null && StringUtils.equals(mldStatus, mldDtl.getStatus());
    }

    /**
     * @param taskId     工单id
     * @param taskStatus 工单状态
     * @return
     */
    @Override
    public Boolean checkTaskMldStatus(Integer taskId, String taskStatus) {

        Task task = TasksCache.get(taskId);
//        System.out.print(task.getTaskId());
//        System.out.print(task.getStatus());
        return task != null && StringUtils.equals(taskStatus, task.getStatus());
    }
}
