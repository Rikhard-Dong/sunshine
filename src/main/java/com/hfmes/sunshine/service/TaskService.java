package com.hfmes.sunshine.service;

import com.hfmes.sunshine.domain.Task;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/12 11:41
 * 工单操作
 */
public interface TaskService {

    /**
     * 工单下发
     *
     * @param deviceId 设备id
     */
    int taskDown(Integer deviceId);

    Task updateTaskFromSql(int devcId,int taskId);
}
