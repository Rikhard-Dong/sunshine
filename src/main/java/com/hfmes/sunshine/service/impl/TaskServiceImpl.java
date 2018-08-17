package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.cache.DevcCache;
import com.hfmes.sunshine.cache.DevcTasksCache;
import com.hfmes.sunshine.cache.TasksCache;
import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.dao.TaskDao;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.enums.TaskStatus;
import com.hfmes.sunshine.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final DevcDao devcDao;
    private final TaskDao taskDao;

    @Autowired
    public TaskServiceImpl(DevcDao devcDao,
                           TaskDao taskDao) {
        this.devcDao = devcDao;
        this.taskDao = taskDao;
    }

    /**
     * 工单下发
     *
     * @param deviceId 设备id
     */
    @Override
    @Transactional
    public int taskDown(Integer deviceId) {
        Task task = new Task();
        List<Task> tasks = taskDao.findByStatusIsST00ByDevcId(deviceId);
        Devc devc = DevcCache.get(deviceId);

        if (devc == null) {
            // TODO error
            return 0;
        }

        boolean flag = false;
        for (Task tmp : tasks) {
            if (StringUtils.equals(tmp.getStatus(), TaskStatus.ST00.toString())) {
                flag = true;
                task = tmp;
                break;
            }
        }

        if (!flag) {
            devc.setTask(task);
            devc.setTaskId(0);
            DevcTasksCache.put(deviceId, tasks);
//            devcMap.put(deviceId, devc);
            devcDao.updateTaskId(devc.getDeviceId(), 0);
            log.warn("警告, 当前设备#{}#没有下一个工单, 设置为当前工单id为0", deviceId);
            return 0;
        }

        // 更新设备的taskId
        devc.setTaskId(task.getTaskId());
        devc.setTask(task);
//        devcMap.put(deviceId, devc);
        DevcTasksCache.put(deviceId, tasks);
        if (devcDao.updateTaskId(devc.getDeviceId(), task.getTaskId()) != 0) {
            return task.getTaskId();
        }
        return 0;
    }


    @Override
    public Task updateTaskFromSql(int devcId, int taskId) {
        Task task = new Task();
        Devc devc = DevcCache.get(devcId);
        if (devc == null) {
            return task;
        }
        task = taskDao.findByTaskId(taskId);

        devc.setTaskId(task.getTaskId());
        devc.setTask(task);
//        devcMap.put(devcId, devc);
        TasksCache.put(taskId, task);

        return task;
    }

}
