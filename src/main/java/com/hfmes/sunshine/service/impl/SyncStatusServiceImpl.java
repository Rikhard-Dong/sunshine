package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.MldDtl;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.service.SyncStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 20:40
 */
@Service
@Slf4j
public class SyncStatusServiceImpl implements SyncStatusService {


    private final Map<Integer, Devc> devcMap;

    private final Map<Integer, MldDtl> mldDtlMap;

    private final Map<Integer, Task> taskMap;

    @Autowired
    public SyncStatusServiceImpl(@Qualifier("devcs") Map<Integer, Devc> devcMap,
                                 @Qualifier("mldDtls") Map<Integer, MldDtl> mldDtlMap,
                                 @Qualifier("tasks") Map<Integer, Task> taskMap) {
        this.devcMap = devcMap;
        this.mldDtlMap = mldDtlMap;
        this.taskMap = taskMap;
    }

    /**
     * @param devcId 设备id
     * @return 同步对象
     */
    @Override
    public Devc syncDevc(Integer devcId) {
        return devcMap.get(devcId);
    }

    /**
     * @param mldId 模具id
     * @return 同步对象
     */
    @Override
    public MldDtl syncMldDtl(Integer mldId) {
        return mldDtlMap.get(mldId);
    }

    /**
     * @param taskId 工单id
     * @return 同步对象
     */
    @Override
    public Task syncTask(Integer taskId) {
        Task task = taskMap.get(taskId);
//        task.setMldDtl(task.getMldDtl());
        return task;
    }
}
