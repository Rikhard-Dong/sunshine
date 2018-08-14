package com.hfmes.sunshine.action;

import com.hfmes.sunshine.dao.DevLogDao;
import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.dao.MldDtlDao;
import com.hfmes.sunshine.dao.TaskDao;
import com.hfmes.sunshine.domain.*;
import com.hfmes.sunshine.enums.TaskStatus;
import com.hfmes.sunshine.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.hfmes.sunshine.utils.Constants.BTN_EVENT_TYPE;
import static com.hfmes.sunshine.utils.Constants.ST;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 19:22
 * <p>
 * action 父类
 */
@Slf4j
public class BaseAction {
    @Autowired
    protected LogService logService;
    @Autowired
    protected MldDtlDao mldDtlDao;
    @Autowired
    protected DevcDao devcDao;
    @Autowired
    protected DevLogDao devLogDao;
    @Autowired
    protected TaskDao taskDao;


    @Autowired
    @Qualifier("devcs")
    protected Map<Integer, Devc> devcMap;

    @Autowired
    @Qualifier("mldDtls")
    protected Map<Integer, MldDtl> mldDtlMap;

    @Autowired
    @Qualifier("persons2")
    protected Map<Integer, Person> personMap;

    @Autowired
    @Qualifier("deviceTasks")
    protected Map<Integer, List<Task>> deviceTaskMap;

    @Autowired
    @Qualifier("tasks")
    protected Map<Integer, Task> tasks;

    protected Integer devcId;
    protected Integer opId;
    protected Integer optionId;
    protected Integer mldDtlId;
    protected Integer taskId;

    // 记录状态
    protected String curStatus;
    protected String nextStatus;
    protected String curTaskStatus;
    protected String nextTaskStatus;

    protected StatusData statusData;

    protected Devc devc;
    protected MldDtl mldDtl;
    protected Task task;

    private Person person;

    /**
     * 加载一些基础属性
     *
     * @param context
     * @param <T>
     * @param <K>
     */
    protected <T, K> void contextLoad(StateContext<T, K> context) {
        devcId = (Integer) context.getMessageHeader("devcId");
        opId = (Integer) context.getMessageHeader("opId");
        optionId = (Integer) context.getMessageHeader("optionId");
        mldDtlId = (Integer) context.getMessageHeader("mldDtlId");


        curStatus = context.getSource().getId().toString();
        nextStatus = context.getTarget().getId().toString();

        curTaskStatus = (String) context.getMessageHeader("curTaskStatus");
        nextTaskStatus = (String) context.getMessageHeader("nextTaskStatus");

        log.debug("curStatus --> {}, nextStatus --> {}", curStatus, nextStatus);

        devc = devcMap.get(devcId);
        mldDtl = mldDtlMap.get(mldDtlId);

        person = personMap.get(opId);
        if (devc == null || mldDtl == null) {
            // TODO 抛出异常回滚
            log.error("对应设备信息为空 --> {}", devc == null);
            log.error("对应模具信息为空 --> {}", mldDtl == null);
            return;
        }

        taskId = devc.getTaskId();
        task = devc.getTask();

    }


    /**
     * 记录模具操作
     */
    protected void mldLog() {
        // 模具操作记录
        MldLog mldLog = new MldLog();
        mldLog.setOpId(opId);
        mldLog.setMldDtlId(mldDtlId);
        mldLog.setOpTime(new Date());
        mldLog.setOpName(person == null ? "" : person.getName());
        logService.mldLog(mldLog);
    }

    /**
     * 记录设备操作
     */
    protected void devLog() {
        DevLog devLog = new DevLog();
        devLog.setDevcId(devcId);
        devLog.setTaskId(taskId);
        devLog.setOpId(opId);
        devLog.setOpTime(new Date());
        devLog.setOpName(person == null ? "" : person.getName());
        logService.devLog(devLog);
    }

    /**
     * 记录状态转换表
     *
     * @param type 状态类型id
     */
    protected void statusDataLog(Integer type) {
        if (type.equals(ST)) {
            statusData.setCurStatus(curTaskStatus);
            statusData.setNextStatus(nextTaskStatus);
        } else {
            statusData.setCurStatus(curStatus);
            statusData.setNextStatus(nextStatus);
        }
        statusData.setOpId(opId);
        statusData.setDevId(devcId);
        statusData.setMldId(mldDtlId);
        statusData.setEventType(BTN_EVENT_TYPE);
        statusData.setEventName(String.valueOf(optionId));

        statusData.setStatusTypeId(type);

        logService.statusDataLog(statusData);
    }

    /**
     * 更新工单信息, 同步更新devc的工单id
     */
    protected void updateTaskStatus() {

        if (task == null) {
            log.error("更新task失败!task 为空");
            // TODO 异常
            return;
        }
        if (!StringUtils.equals(task.getStatus(), curTaskStatus)) {
            log.error("工单状态不符合, 当前工单状态应为为{}, 实际状态为{}", task.getStatus(), curTaskStatus);
            // TODO 异常
            return;
        }
        // 更新工单状态
        task.setStatus(nextTaskStatus);
        taskDao.updateStatus(task.getTaskId(), nextTaskStatus);

        // 更新工单信息
        devc.setTask(task);
    }

    protected void updateDevcStatus() {
        devc.setStatus(nextStatus);
        devcDao.updateStatus(devc.getDeviceId(), nextStatus);
    }
}
