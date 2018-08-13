package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.MldLog;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 16:14
 */

public interface MldLogDao {

    /**
     * 新增一条mld操作日志
     *
     * @param mldLog mld日志
     * @return 更新行数
     */
    Integer insertOne(MldLog mldLog);
}
