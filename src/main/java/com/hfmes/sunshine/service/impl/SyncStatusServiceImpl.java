package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.cache.*;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.MldDtl;
import com.hfmes.sunshine.domain.Person;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.service.SyncStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 20:40
 */
@Service
@Slf4j
public class SyncStatusServiceImpl implements SyncStatusService {


    /**
     * @param devcId 设备id
     * @return 同步对象
     */
    @Override
    public Devc syncDevc(Integer devcId) {
        return DevcCache.get(devcId);
    }

    /**
     * @param mldId 模具id
     * @return 同步对象
     */
    @Override
    public MldDtl syncMldDtl(Integer mldId) {
        return MldDtlsCache.get(mldId);
    }

    /**
     * @param taskId 工单id
     * @return 同步对象
     */
    @Override
    public Task syncTask(Integer taskId) {
        // task.setMldDtl(task.getMldDtl());
        return TasksCache.get(taskId);
    }

    @Override
    public Map<String, Person> getAllPersonList() {
        return PersonCache.getMap();
    }
}
