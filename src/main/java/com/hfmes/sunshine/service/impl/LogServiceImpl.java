package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.cache.CountNumsCache;
import com.hfmes.sunshine.cache.OptionsCache;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

/*    @Autowired
    @Qualifier("deviceStatusDatas")
    private Map<Integer, Map<Integer, StatusData>> deviceStatusDataMap;*/


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

        String eventName = OptionsCache.get(Integer.valueOf(statusData.getEventName()));
        statusData.setEventName(eventName);
        statusData.setStart(curDate);
        statusData.setStop(curDate);

        StatusData preStatusData = statusDataDao.findByDevcIdAndTypeTop1(statusData.getDevId(), statusData.getStatusTypeId());
        if (preStatusData != null) {
            preStatusData.setStop(curDate);
            Long diff = DateUtils.calculateMinuteDifference(preStatusData.getStart(), curDate);
            preStatusData.setHold(Math.toIntExact(diff));
            preStatusData.setCount(CountNumsCache.get(preStatusData.getDevId()));
            statusDataDao.updateEndAdnHold(preStatusData);
//            log.debug("前一次状态记录 --> {}", preStatusData);
        } else {
            log.info("前一次状态记录为null");
        }
        /*
        Map<Integer, StatusData> statusDataMap = deviceStatusDataMap.get(statusData.getDevId());
        if (statusDataMap != null) {
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
                log.info("更新前一次状态转换记录为{}", preStatusData);
                log.info("更新前一次状态转换结果, {}", result);
                statusDataMap.remove(statusData.getStatusTypeId());
            } else {
                log.info("前一次状态记录为null");
            }
        } else {
            statusDataMap = new HashMap<>();
        }

        log.info("状态记录信息map -> {}", deviceStatusDataMap);
        */
        Integer result = statusDataDao.insertOne(statusData);
        // 将本次操作记录
//        statusDataMap.put(statusData.getStatusTypeId(), statusData);
//        deviceStatusDataMap.put(statusData.getDevId(), statusDataMap);
        return result == 1;
    }

    @Override
    public Boolean devLog(DevLog devLog) {
        return devLogDao.insertOne(devLog) == 1;
    }
}
