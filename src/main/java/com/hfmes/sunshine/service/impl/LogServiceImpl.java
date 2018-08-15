package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.DevLogDao;
import com.hfmes.sunshine.dao.MldLogDao;
import com.hfmes.sunshine.dao.StatusDataDao;
import com.hfmes.sunshine.domain.DevLog;
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
    private final DevLogDao devLogDao;

    @Autowired
    @Qualifier("options")
    private Map<Integer, String> optionsMap;

    @Autowired
    @Qualifier("deviceStatusDatas")
    private Map<Integer, Map<Integer, StatusData>> deviceStatusDataMap;

    @Autowired
    @Qualifier("countNums")
    private Map<Integer, Integer> countNums;

    @Autowired
    public LogServiceImpl(MldLogDao mldLogDao,
                          StatusDataDao statusDataDao,
                          DevLogDao devLogDao) {
        this.mldLogDao = mldLogDao;
        this.statusDataDao = statusDataDao;
        this.devLogDao = devLogDao;
    }

    @Override
    public Boolean mldLog(MldLog mldLog) {
        return mldLogDao.insertOne(mldLog) == 1;
    }

    @Override
    @Transactional
    public Boolean statusDataLog(StatusData statusData) {
        Date curDate = new Date();

        String eventName = optionsMap.get(Integer.valueOf(statusData.getEventName()));
        statusData.setEventName(eventName);
        statusData.setStart(curDate);

        Map<Integer, StatusData> statusDataMap = deviceStatusDataMap.get(statusData.getDevId());
        log.info("当前状态转换的类型为type=={}", statusData.getStatusTypeId());
        StatusData preStatusData = statusDataMap.get(statusData.getStatusTypeId());
        if (preStatusData != null) {
            // 更新上一步操作的结束时间和存续时间
            preStatusData.setStop(curDate);
            Long diff = DateUtils.calculateMinuteDifference(preStatusData.getStart(), curDate);
            preStatusData.setHold(Math.toIntExact(diff));
            preStatusData.setCount(countNums.get(preStatusData.getDevId()));
            log.info("前一次状态转换id为{}, 状态转换之间的count数量统计为{}, preStatusData.getCount() -> {}", preStatusData.getStatusDataId(),
                    countNums.get(preStatusData.getDevId()), preStatusData.getCount());

            Integer result = statusDataDao.updateEndAdnHold(preStatusData);
            log.info("更新前一次状态转换结果, {}", result);
        }

        // 将本次操作记录
        statusDataMap.put(statusData.getStatusTypeId(), statusData);

        deviceStatusDataMap.put(statusData.getDevId(), statusDataMap);

        return statusDataDao.insertOne(statusData) == 1;
    }

    @Override
    public Boolean devLog(DevLog devLog) {
        return devLogDao.insertOne(devLog) == 1;
    }
}
