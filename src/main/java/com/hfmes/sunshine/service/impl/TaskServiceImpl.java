package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.dao.TaskDao;
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

    private final TaskDao taskDao;

    @Autowired
    public TaskServiceImpl(DevcDao devcDao,
                           TaskDao taskDao,
                           @Qualifier("deviceTasks") Map<Integer, List<Task>> deviceTasks,
                           @Qualifier("devcs") Map<Integer, Devc> devcMap,
                           @Qualifier("tasks") Map<Integer, Task> taskMap) {
        this.devcDao = devcDao;
        this.deviceTasks = deviceTasks;
        this.devcMap = devcMap;
        this.taskMap = taskMap;
        this.taskDao=taskDao;
    }

    /**
     * 工单下发
     *
     * @param deviceId 设备id
     */
    @Override
    @Transactional
    public int taskDown(Integer deviceId) {
        Task task=new Task();
        List<Task> tasks=taskDao.findByStatusIsST00ByDevcId(deviceId);
        if (tasks == null || tasks.size() <= 0) {
            return 0;
        }
        Devc devc = devcMap.get(deviceId);
        if (devc == null) {
            return 0;
        }

        task = tasks.get(0);
        // 移除相关数据
        deviceTasks.put(deviceId,tasks);

        // 更新设备的taskId
        devc.setTaskId(task.getTaskId());
        devc.setTask(task);
        deviceTasks.put(deviceId,tasks);
        if (devcDao.updateTaskId(devc.getDeviceId(), task.getTaskId()) != 0) {
            return task.getTaskId();
        }
        return 0;
    }


    @Override
    public Task updateTaskFromSql(int devcId,int taskId) {
        Task task=new Task();
        Devc devc = devcMap.get(devcId);
        if (devc == null) {
            return task;
        }
        task=taskDao.findByTaskId(taskId);
        devc.setTaskId(task.getTaskId());
        devc.setTask(task);
        devcMap.put(devcId,devc);
        return task;
    }

}
