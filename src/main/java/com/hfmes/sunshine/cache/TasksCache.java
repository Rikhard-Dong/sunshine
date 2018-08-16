package com.hfmes.sunshine.cache;

import com.hfmes.sunshine.dao.TaskDao;
import com.hfmes.sunshine.domain.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/16 18:51
 * 存放工单信息缓存
 * <p>
 * key task id
 * value task
 */
@Slf4j
public class TasksCache {
    private static Map<Integer, Task> cache;

    private TasksCache() {
    }

    public static void init(TaskDao taskDao) {
        log.info("初始化tasks缓存...");
        cache = new ConcurrentHashMap<>();
        List<Task> tasks = taskDao.findByStatusIsST00AndDevTask();
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                cache.put(task.getTaskId(), task);
            }
        } else {
            log.warn("初始化tasks缓存警告 --> 符合条件的工单为空");
        }

    }


    public static void put(Integer taskId, Task task) {
        cache.put(taskId, task);
    }

    public static Task get(Integer taskId) {
        return cache.get(taskId);
    }

    public static void remove(Integer taskId) {
        cache.remove(taskId);
    }
}
