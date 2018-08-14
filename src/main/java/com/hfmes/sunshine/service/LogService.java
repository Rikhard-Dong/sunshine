package com.hfmes.sunshine.service;

import com.hfmes.sunshine.domain.DevLog;
import com.hfmes.sunshine.domain.MldLog;
import com.hfmes.sunshine.domain.StatusData;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 17:09
 */
public interface LogService {

    /**
     * 模具操作日志记录
     *
     * @param mldLog 模具操作
     * @return 日志记录
     */
    Boolean mldLog(MldLog mldLog);

    /**
     * 操作状态转换
     *
     * @param statusData 状态转换
     * @return 状态转换记录
     */
    Boolean statusDataLog(StatusData statusData);

    /**
     * 设备操作日志记录
     *
     * @param devLog 设备操作
     * @return
     */
    Boolean devLog(DevLog devLog);
}
