package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.StatusData;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 8:37
 */
public interface StatusDataDao {
    /**
     * 插入一条数据
     *
     * @param data 数据
     * @return 改变行数
     */
    Integer insertOne(StatusData data);


}
