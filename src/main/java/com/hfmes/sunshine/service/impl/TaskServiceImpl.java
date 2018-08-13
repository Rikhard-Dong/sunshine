package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/12 11:42
 */
@Service
public class TaskServiceImpl implements TaskService {
    private final DevcDao devcDao;

    private final Map<Integer, List<Task>> deviceTasks;
    private final Map<Integer, Devc> devcMap;
    private final Map<Integer, Task> taskMap;

    @Autowired
    public TaskServiceImpl(DevcDao devcDao,
                           @Qualifier("deviceTasks") Map<Integer, List<Task>> deviceTasks,
                           @Qualifier("devcs") Map<Integer, Devc> devcMap,
                           @Qualifier("tasks") Map<Integer, Task> taskMap) {
        this.devcDao = devcDao;
        this.deviceTasks = deviceTasks;
        this.devcMap = devcMap;
        this.taskMap = taskMap;
    }

    /**
     * 工单下发
     *
     * @param deviceId 设备id
     */
    @Override
    @Transactional
    public void taskDown(Integer deviceId) {
        List<Task> tasks = deviceTasks.get(deviceId);
        if (tasks == null || tasks.size() <= 0) {
            return;
        }

        Devc devc = devcMap.get(deviceId);
        if (devc == null) {
            return;
        }

        Task task = tasks.get(0);
        // 更新设备的taskId
        devc.setTaskId(task.getTaskId());
        devc.setTask(task);
        if (devcDao.updateTaskId(devc.getDeviceId(), task.getTaskId()) == 0) {
            // TODO 更新条数为0, 抛出异常回滚
            return;
        }
        // 移除相关数据
        tasks.remove(task);
        taskMap.remove(task.getTaskId());

    }
}
