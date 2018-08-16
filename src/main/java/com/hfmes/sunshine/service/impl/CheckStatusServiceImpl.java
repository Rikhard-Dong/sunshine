package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.MldDtl;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.service.CheckStatusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 15:58
 * <p>
 * 检查咋黄台同步情况
 */
@Service
public class CheckStatusServiceImpl implements CheckStatusService {

    private final Map<Integer, Devc> devcMap;

    private final Map<Integer, MldDtl> mldDtlMap;

    private final Map<Integer, Task> taskMap;

    @Autowired
    public CheckStatusServiceImpl(@Qualifier("devcs") Map<Integer, Devc> devcMap,
                                  @Qualifier("mldDtls") Map<Integer, MldDtl> mldDtlMap,
                                  @Qualifier("tasks") Map<Integer, Task> taskMap) {
        this.devcMap = devcMap;
        this.mldDtlMap = mldDtlMap;
        this.taskMap = taskMap;
    }

    /**
     * @param deviceId   设备id
     * @param devcStatus 下位机上传状态
     * @return
     */
    @Override
    public Boolean checkDevcStatus(Integer deviceId, String devcStatus) {
        Devc devc = devcMap.get(deviceId);

        return devc != null && StringUtils.equals(devcStatus, devc.getStatus());
    }

    /**
     * @param mldId     模具id
     * @param mldStatus 模具状态
     * @return
     */
    @Override
    public Boolean checkMldStatus(Integer mldId, String mldStatus) {
        MldDtl mldDtl = mldDtlMap.get(mldId);
        return mldDtl != null && StringUtils.equals(mldStatus, mldDtl.getStatus());
    }

    /**
     * @param taskId     工单id
     * @param taskStatus 工单状态
     * @return
     */
    @Override
    public Boolean checkTaskMldStatus(Integer taskId, String taskStatus) {

        Task task = taskMap.get(taskId);
//        System.out.print(task.getTaskId());
//        System.out.print(task.getStatus());
        return task != null && StringUtils.equals(taskStatus, task.getStatus());
    }
}
