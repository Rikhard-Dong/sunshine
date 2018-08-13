package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.MldLogDao;
import com.hfmes.sunshine.dao.StatusDataDao;
import com.hfmes.sunshine.domain.MldLog;
import com.hfmes.sunshine.domain.StatusData;
import com.hfmes.sunshine.service.LogService;
import com.hfmes.sunshine.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 17:10
 */
@Service
@Slf4j
public class LogServiceImpl implements LogService {


    private final MldLogDao mldLogDao;
    private final StatusDataDao statusDataDao;

    @Autowired
    @Qualifier("methods")
    private Map<Integer, String> methodMap;

    @Autowired
    @Qualifier("deviceStatusDatas")
    private Map<Integer, Map<Integer, StatusData>> deviceStatusDataMap;

    @Autowired
    @Qualifier("countNums")
    private Map<Integer, Integer> countNums;

    @Autowired
    public LogServiceImpl(MldLogDao mldLogDao,
                          StatusDataDao statusDataDao) {
        this.mldLogDao = mldLogDao;
        this.statusDataDao = statusDataDao;
    }

    @Override
    public Boolean mkdLog(MldLog mldLog) {
        return mldLogDao.insertOne(mldLog) == 1;
    }

    @Override
    @Transactional
    public Boolean statusDataLog(StatusData statusData) {
        Date curDate = new Date();

        String eventName = methodMap.get(Integer.valueOf(statusData.getEventName()));
        statusData.setEventName(eventName);
        statusData.setStart(curDate);
        statusData.setCount(countNums.get(statusData.getDevId()));

        Map<Integer, StatusData> statusDataMap = deviceStatusDataMap.get(statusData.getDevId());
        StatusData preStatusData = statusDataMap.get(statusData.getStatusTypeId());
        if (preStatusData != null) {
            // 更新上一步操作的结束时间和存续时间
            preStatusData.setStop(curDate);
            Long diff = DateUtils.calculateMinuteDifference(preStatusData.getStart(), curDate);
            preStatusData.setHold(Math.toIntExact(diff));

            statusDataDao.updateEndAdnHold(preStatusData);
        }

        // 将本次操作记录
        statusDataMap.put(statusData.getStatusTypeId(), statusData);

        return statusDataDao.insertOne(statusData) == 1;
    }
}
