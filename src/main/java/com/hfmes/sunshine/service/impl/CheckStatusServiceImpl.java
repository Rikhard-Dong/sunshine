package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.MldDtlDao;
import com.hfmes.sunshine.dao.TaskDao;
import com.hfmes.sunshine.domain.Devc;
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

    private final Map<Integer, Devc> devcs;

    private final MldDtlDao mldDtlDao;
    private final TaskDao taskDao;

    @Autowired
    public CheckStatusServiceImpl(@Qualifier("devcs") Map<Integer, Devc> devcs,
                                  MldDtlDao mldDtlDao,
                                  TaskDao taskDao) {
        this.devcs = devcs;
        this.mldDtlDao = mldDtlDao;
        this.taskDao = taskDao;
    }

    /**
     * @param deviceId   设备id
     * @param devcStatus 下位机上传状态
     * @return
     */
    @Override
    public Boolean checkDevcStatus(Integer deviceId, String devcStatus) {
        Devc devc = devcs.get(deviceId);
        if (devc == null) {
            return Boolean.FALSE;
        }
        return StringUtils.equals(devcStatus, devc.getStatus());
    }

    /**
     * @param mldId     模具id
     * @param mldStatus 模具状态
     * @return
     */
    @Override
    public Boolean checkMldStatus(Integer mldId, String mldStatus) {
        String curStatus = mldDtlDao.getStatusByMldId(mldId);
        return StringUtils.equals(mldStatus, curStatus);
    }

    /**
     * @param taskId     工单id
     * @param taskStatus 工单状态
     * @return
     */
    @Override
    public Boolean checkTaskMldStatus(Integer taskId, String taskStatus) {
        String curStatus = taskDao.getStatusByTaskId(taskId);
        return StringUtils.equals(taskStatus, curStatus);
    }
}
