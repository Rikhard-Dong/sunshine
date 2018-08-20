package com.hfmes.sunshine.service;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.MldDtl;
import com.hfmes.sunshine.domain.Person;
import com.hfmes.sunshine.domain.Task;

import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 20:40
 * <p>
 * 同步状态服务类
 */
public interface SyncStatusService {

    /**
     * 实现设备状态同步
     *
     * @param devcId 设备id
     * @return 同步对象
     */
    Devc syncDevc(Integer devcId);

    /**
     * 实现模具状态同步
     *
     * @param mldId 模具id
     * @return 同步对象
     */
    MldDtl syncMldDtl(Integer mldId);

    /**
     * 实现工单态同步
     *
     * @param taskId 工单id
     * @return 同步对象
     */
    Task syncTask(Integer taskId);

    Map<String, Person> getAllPersonList();
}
