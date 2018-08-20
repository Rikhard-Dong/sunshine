package com.hfmes.sunshine.action.devc;

import com.hfmes.sunshine.action.BaseAction;
import com.hfmes.sunshine.cache.DevcCache;
import com.hfmes.sunshine.cache.Person2Cache;
import com.hfmes.sunshine.cache.TasksCache;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.hfmes.sunshine.utils.Constants.SD;
import static com.hfmes.sunshine.utils.Constants.ST;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 9:24
 * <p>
 * 开始生产action
 */
@Component
@Slf4j
public class ProduceStartAction extends BaseAction implements Action<DeviceStatus, DeviceEvents> {

    @Override
    @Transactional
    public void execute(StateContext<DeviceStatus, DeviceEvents> context) {
        log.debug("开始生产");

        contextLoad(context);
        updateNum();

        // 记录信息
        devLog("开始操作", "", "操作");

        // 更新工单状态
        updateTaskStatus();
        updateDevcStatus();

        Task task = TasksCache.get(taskId);
        Devc devc = DevcCache.get(devcId);

        if (task.getDevOpId() == 0) {
            log.warn("before task#{}# -> opId#{}#", taskId, TasksCache.get(taskId).getDevOpId());
            task.setDevOpId(opId);
            task.setDevOp(Person2Cache.get(opId));
            taskDao.updateDevOpId(task.getTaskId(), opId);
            log.warn("after task#{}# -> opId#{}#", taskId, TasksCache.get(taskId).getDevOpId());
        }
        task.setMldStartTime(new Date());
        taskDao.updateStartTime(taskId, task.getMldStartTime());
        devc.setTask(task);

        statusDataLog(SD);
        statusDataLog(ST);
        resetCounts();

        // tasks和deviceTaskMap中只存放状态为分配的工单
//        tasks.remove(task.getTaskId());
//        List<Task> tasksForDeviceId = deviceTaskMap.get(devcId);
//        tasksForDeviceId.remove(task);
//        deviceTaskMap.put(devcId, tasksForDeviceId);


    }
}
