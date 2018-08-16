package com.hfmes.sunshine.cache;

import com.hfmes.sunshine.dao.TaskDao;
import com.hfmes.sunshine.domain.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/16 18:38
 * <p>
 * key devcId value task list of device id
 */
@Slf4j
public class DevcTasksCache {
    private static Map<Integer, List<Task>> cache;

    private DevcTasksCache() {

    }

    public static void init(TaskDao taskDao) {
        log.info("初始化设备工单缓存...");

        cache = new ConcurrentHashMap<>();
        List<Task> tasks = taskDao.findByStatusIsST00AndDevTask();

        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                if (task.getDevcId() != null) {
                    List<Task> tmp = cache.computeIfAbsent(task.getDevcId(), k -> new ArrayList<>());
                    tmp.add(task);
                }
            }
        } else {
            log.warn("设备工单缓存警告 --> 当前没有工单信息");
        }
    }

    public static void put(Integer devcId, List<Task> tasks) {
        cache.put(devcId, tasks);
    }

    public static List<Task> get(Integer devcId) {
        return cache.get(devcId);
    }

    public static void remove(Integer devcId) {
        cache.remove(devcId);
    }
}

