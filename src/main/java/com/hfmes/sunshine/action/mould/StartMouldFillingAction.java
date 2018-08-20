package com.hfmes.sunshine.action.mould;

import com.hfmes.sunshine.action.BaseAction;
import com.hfmes.sunshine.cache.DevcCache;
import com.hfmes.sunshine.cache.MldDtlsCache;
import com.hfmes.sunshine.cache.Person2Cache;
import com.hfmes.sunshine.cache.TasksCache;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.MldDtl;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.hfmes.sunshine.utils.Constants.SM;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 11:04
 * 开始装模action
 */
@Component
@Slf4j
public class StartMouldFillingAction extends BaseAction implements Action<MouldStatus, MouldEvents> {

    @Override
    @Transactional
    public void execute(StateContext<MouldStatus, MouldEvents> context) {
        log.debug("开始装模action...");
        contextLoad(context);

        Devc devc = DevcCache.get(devcId);
        updateNum();
        // 记录操作
        mldLog("装模操作开始", "", "操作");


        // 更新相关数据表
        // 更新模具状态
        log.debug("before mldDtl status --> {}", MldDtlsCache.get(mldDtlId).getStatus());
        updateMldStatus();
        log.debug("after mldDtl status --> {}", MldDtlsCache.get(mldDtlId).getStatus());
        log.info("#####\n#####\n#####\n对象是否一致 -> {}\n#####\n#####\n#####\n", devc == DevcCache.get(devcId));

        // 更新设备信息
        updateDevcMldDltStatus();
        Task task = TasksCache.get(taskId);
        task.setMldStartTime(new Date());
        if (!task.getMldOpId().equals(opId)) {
            task.setMldOpId(opId);
            task.setMldOp(Person2Cache.get(opId));
            taskDao.updateMldOp(taskId, opId);
        }
        taskDao.updateMLdStartTime(task.getTaskId(), task.getMldStartTime());
        devc.setTask(task);

        // 记录状态转换
        statusDataLog(SM);
        resetCounts();
    }
}
