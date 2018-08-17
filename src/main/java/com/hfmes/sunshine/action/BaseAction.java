package com.hfmes.sunshine.action;

import com.hfmes.sunshine.cache.*;
import com.hfmes.sunshine.dao.*;
import com.hfmes.sunshine.domain.*;
import com.hfmes.sunshine.enums.MouldStatus;
import com.hfmes.sunshine.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;

import java.util.Date;

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
    protected DevRprDao devRprDao;
    @Autowired
    protected MldRprDao mldRprDao;
    @Autowired
    protected PlanDtlDao planDtlDao;

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

    protected StatusData statusData = new StatusData();

//    protected Devc devc;
//    protected MldDtl mldDtl;
//    protected Task task;
//
//    private Person person;

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

//        devc = DevcCache.get(devcId);
//        mldDtl = MldDtlsCache.get(mldDtlId);
//
//        person = Person2Cache.get(opId);
//        if (devc == null || mldDtl == null) {
//            // TODO 抛出异常回滚
//            log.error("对应设备信息为空 --> {}", devc == null);
//            log.error("对应模具信息为空 --> {}", mldDtl == null);
//            return;
//        }

        taskId = DevcCache.get(devcId).getTaskId();
//        task = devc.getTask();

    }


    /**
     * 记录模具操作
     */
    protected void mldLog(String opDesc, String opName, String opType) {
        // 模具操作记录
        MldLog mldLog = new MldLog();
        mldLog.setOpId(opId);
        mldLog.setTaskId(taskId == null ? 0 : taskId);
        mldLog.setMldDtlId(mldDtlId);
        mldLog.setOpTime(new Date());
        mldLog.setTaskId(taskId == null ? 0 : taskId);
//        mldLog.setOpName(person == null ? "" : person.getName());
        mldLog.setOpName(Person2Cache.get(opId) == null ? "" : Person2Cache.get(opId).getName());
        mldLog.setOpDesc(opDesc);
        mldLog.setOpName(opName);
        mldLog.setOpType(opType);
        logService.mldLog(mldLog);
    }

    /**
     * 记录设备操作
     */
    protected void devLog(String opDesc, String opName, String opType) {
        DevLog devLog = new DevLog();
        devLog.setDevcId(devcId);
        devLog.setTaskId(taskId);
        devLog.setOpId(opId);
        devLog.setOpTime(new Date());
//        devLog.setOpName(person == null ? "" : person.getName());
        devLog.setOpName(Person2Cache.get(opId) == null ? "" : Person2Cache.get(opId).getName());
        devLog.setOpDesc(opDesc);
        devLog.setOpName(opName);
        devLog.setOpType(opType);
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
        statusData.setTaskId(taskId);
        statusData.setEventType(BTN_EVENT_TYPE);
        statusData.setEventName(String.valueOf(optionId));

        statusData.setStatusTypeId(type);

        logService.statusDataLog(statusData);
    }

    /**
     * 更新工单信息, 同步更新devc的工单id
     */
    protected void updateTaskStatus() {
        Task task = TasksCache.get(taskId);
        Devc devc = DevcCache.get(devcId);

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

        // 更新设备的工单信息
        devc.setTask(task);
    }

    /**
     * 更新模具状态
     */
    protected void updateMldStatus() {
        MldDtl mldDtl = MldDtlsCache.get(mldDtlId);
        Task task = TasksCache.get(taskId);
        Devc devc = DevcCache.get(devcId);

        mldDtl.setStatus(nextStatus);
        mldDtlDao.updateStatus(mldDtlId, nextStatus);
        task.setMldDtl(mldDtl);
        log.info("###### task mldDtl -> {}", task.getMldDtl());
        log.info("###### task cache mldDtl -> {}", TasksCache.get(taskId).getMldDtl());
        devc.setTask(task);
    }

    /**
     * 更新设备中模具状态信息
     */
    protected void updateDevcMldDltStatus() {
        MldDtl mldDtl = MldDtlsCache.get(mldDtlId);
        Devc devc = DevcCache.get(devcId);

        devc.setMldDtlId(mldDtlId);
        devc.setMldDtl(mldDtl);
        devc.setMldStatus(mldDtl.getStatus());
        devcDao.updateMldDtlIdAndMldStatus(devcId, mldDtlId, nextStatus);
    }

    protected void resetCounts() {
        CountNumsCache.put(devcId, 0);
    }

    /**
     * 移除模具
     */
    protected void removeDevcMld() {
        Devc devc = DevcCache.get(devcId);

        devc.setMldDtlId(null);
        devc.setMldDtl(null);
        devc.setMldStatus(null);

        devcDao.updateMldDtlIdAndMldStatus(devcId, null, "");

    }

    /**
     * 更新设备状态
     */
    protected void updateDevcStatus() {
        Devc devc = DevcCache.get(devcId);

        log.warn("devcId --> {}, 更新设备状态为:--> {}", devc.getDeviceId(), nextStatus);
        devc.setStatus(nextStatus);
        Integer result = devcDao.updateStatus(devc.getDeviceId(), nextStatus);
        log.warn("执行结果 -> {}", result);
    }

    /**
     * 添加一条设备维修记录
     */
    protected void addDevRpr() {
        Person person = Person2Cache.get(opId);

        DevRpr devRpr = new DevRpr();
        devRpr.setDevcId(devcId);
        devRpr.setFaltTime(new Date());
        devRpr.setReporter(person.getName());
        devRpr.setFault("设备故障");
        devRprDao.insertOne(devRpr);
    }

    /**
     * 添加一条模具维修记录
     */
    protected void addMldRpr() {
        Person person = Person2Cache.get(opId);

        MldRpr mldRpr = new MldRpr();
        mldRpr.setMldDtlId(mldDtlId);
        mldRpr.setFaltTime(new Date());
        mldRpr.setReporter(person.getName());
        mldRpr.setFault("模具故障");
        mldRprDao.insertOne(mldRpr);
    }

    /**
     * 开始维修设备, 更新开始时间及维修人员
     */
    protected void startDevRpr() {
        DevRpr devRpr = devRprDao.findByDevcIdTop1(devcId);
        if (devRpr == null) {
            log.error("没有设备维修信息, devcId --> {}", devcId);
            return;
        }
        devRpr.setStartTime(new Date());
        devRpr.setRepairerId(opId);
        devRprDao.updateRepairer(devRpr.getDevRprId(), opId, "", devRpr.getStartTime());
    }

    /**
     * 开始模具维修
     */
    protected void startMldRpr() {
        MldRpr mldRpr = mldRprDao.findByMldIdTop1(mldDtlId);
        if (mldRpr == null) {
            log.error("没有设备维修信息, mldDtlId --> {}", mldDtlId);
            return;
        }

        mldRpr.setStartTime(new Date());
        mldRpr.setRprId(opId);
        mldRprDao.updateRepairer(mldRpr.getMldRprId(), opId, "", mldRpr.getStartTime());
    }


    /**
     * 设备维修结束, 记录结束时间
     */
    protected void endDevRpr() {
        DevRpr devRpr = devRprDao.findByDevcIdTop1(devcId);
        if (devRpr == null) {
            log.error("没有设备维修信息, devcId --> {}", devcId);
            return;
        }
        devRprDao.updateCompleteRepair(devRpr.getDevRprId(), "", new Date());
    }

    protected void endMldRpr() {
        MldRpr mldRpr = mldRprDao.findByMldIdTop1(mldDtlId);
        if (mldRpr == null) {
            log.error("没有设备维修信息, mldDtlId --> {}", mldDtlId);
            return;
        }
        mldRprDao.updateCompleteRepair(mldRpr.getMldRprId(), "", new Date());
    }

    /**
     * 撤销报修, 记录结束时间
     */
    protected void revokeDevRpr() {
        DevRpr devRpr = devRprDao.findByDevcIdTop1(devcId);
        if (devRpr == null) {
            log.error("没有设备维修信息, devcId --> {}", devcId);
            return;
        }
        devRprDao.updateCompleteRepair(devRpr.getDevRprId(), "撤销报修", new Date());
    }

    /**
     * 撤销模具报修, 记录结束时间
     */
    protected void revokeMldRpr() {
        MldRpr mldRpr = mldRprDao.findByMldIdTop1(mldDtlId);
        if (mldRpr == null) {
            log.error("没有设备维修信息, mldDtlId --> {}", mldDtlId);
            return;
        }
        mldRprDao.updateCompleteRepair(mldRpr.getMldRprId(), "", new Date());
    }

    /**
     * 更新工单中的数量信息
     */
    protected void updateNum() {
        Devc devc = DevcCache.get(devcId);

        if (devc != null) {
            if (StringUtils.equals(devc.getMldStatus(), MouldStatus.SM40.toString())) {
                log.info("更新生产数量 id ->  {}., num -> {}", taskId, devc.getTask().getProcNum());
                taskDao.updateProcNum(taskId, devc.getTask().getProcNum());
            } else {
                log.info("更新测试数量 id ->  {}, num -> {}", taskId, devc.getTask().getTestNum());
                taskDao.updateTestNum(taskId, devc.getTask().getTestNum());
            }
        } else {
            log.warn("devc is null");
        }
    }

    /**
     * 更新planDtl相关数据
     */
    protected void updatePlanDtl() {
        Task task = TasksCache.get(taskId);

        PlanDtl planDtl = planDtlDao.findById(task.getPlanDtlId());
        Integer sum = taskDao.sumProcNumByPlanDtlId(task.getPlanDtlId());
        if (sum == null || sum <= 0) {
            log.error("taskId->{}, planDtlId->{}, 统计生产数量小于等于零", taskId, task.getPlanDtlId());
            return;
        }
        if (planDtl != null) {
            if (sum >= planDtl.getReqNum()) {
                planDtlDao.updateCmpNumAndComplete(task.getPlanDtlId(), sum);
            } else {
                planDtlDao.updateCmpNum(task.getPlanDtlId(), sum);
            }
        } else {
            log.error("当前工单#{}#对应的planDtl#{}#为空", task.getTaskId(), task.getPlanDtlId());
        }
    }
}
