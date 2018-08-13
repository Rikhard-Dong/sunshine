package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.StatusData;
import org.apache.ibatis.annotations.Param;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 8:37
 */
public interface StatusDataDao {

    /* ****************************************
     * insert
     ******************************************/

    /**
     * 插入一条数据
     *
     * @param data 数据
     * @return 改变行数
     */
    Integer insertOne(StatusData data);


    /* ****************************************
     * update
     ******************************************/

    /**
     * 更新结束时间和存续时间
     *
     * @param data data
     * @return 更新函数
     */
    Integer updateEndAdnHold(StatusData data);

    /* ****************************************
     * select
     ******************************************/

    /**
     * 获取设备的对应状态的状态转换记录, 只取最近的结束时间为null的一条记录
     *
     * @param devcId 设备id
     * @param type   状态类型
     * @return status data
     */
    StatusData findByDevcIdAndTypeTop1(@Param("devcId") Integer devcId,
                                       @Param("statusTypeId") Integer type);

}
