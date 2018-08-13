package com.hfmes.sunshine.action;

import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.dao.MldDtlDao;
import com.hfmes.sunshine.domain.*;
import com.hfmes.sunshine.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateContext;

import java.util.Date;
import java.util.Map;

import static com.hfmes.sunshine.utils.Constants.BTN_EVENT_TYPE;

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
    @Qualifier("devcs")
    protected Map<Integer, Devc> devcMap;

    @Autowired
    @Qualifier("mldDtls")
    protected Map<Integer, MldDtl> mldDtlMap;

    @Autowired
    @Qualifier("persons2")
    private Map<Integer, Person> personMap;


    protected Integer devcId;
    protected Integer opId;
    protected Integer optionId;
    protected Integer mldDtlId;

    // 记录状态
    protected String curStatus;
    protected String nextStatus;

    protected StatusData statusData;


    protected Devc devc;
    protected MldDtl mldDtl;

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

        log.debug("curStatus --> {}, nextStatus --> {}", curStatus, nextStatus);

        devc = devcMap.get(devcId);
        mldDtl = mldDtlMap.get(mldDtlId);
        if (devc == null || mldDtl == null) {
            // TODO 抛出异常回滚
            return;
        }
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
        Person person = personMap.get(opId);
        mldLog.setOpName(person == null ? "" : person.getName());
        logService.mkdLog(mldLog);
    }

    /**
     * 记录状态转换表
     *
     * @param type 状态类型id
     */
    protected void statusDataLog(Integer type) {
        statusData.setCurStatus(curStatus);
        statusData.setNextStatus(nextStatus);
        statusData.setOpId(opId);
        statusData.setDevId(devcId);
        statusData.setMldId(mldDtlId);
        statusData.setEventType(BTN_EVENT_TYPE);
        statusData.setEventName(String.valueOf(optionId));

        statusData.setStatusTypeId(type);

        logService.statusDataLog(statusData);
    }
}
