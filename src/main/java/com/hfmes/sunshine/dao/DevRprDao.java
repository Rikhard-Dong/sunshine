package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.DevRpr;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/14 15:22
 */
public interface DevRprDao {

    /* ****************************************
     * insert
     ******************************************/

    /**
     * @param devRpr
     * @return
     */
    Integer insertOne(DevRpr devRpr);

    /* ****************************************
     * update
     ******************************************/

    /**
     * @param devRprId
     * @param repairerId
     * @param rprName
     * @param startTime
     * @return
     */
    Integer updateRepairer(@Param("devRprId") Integer devRprId,
                           @Param("repairerId") Integer repairerId,
                           @Param("rprName") String rprName,
                           @Param("startTime") Date startTime);

    /**
     * @param devRprId
     * @param endTime
     * @return
     */
    Integer updateCompleteRepair(@Param("devRprId") Integer devRprId,
                                 @Param("reprDesc") String reprDesc,
                                 @Param("endTime") Date endTime);

    /* ****************************************
     * select
     ******************************************/

    /**
     * 获取所有未修复完成的报修信息
     *
     * @return
     */
    List<DevRpr> findUnComplete();

    /**
     * 找到最新的一条设备维修记录, 根据报修时间排序
     *
     * @param devcId
     * @return
     */
    DevRpr findByDevcIdTop1(Integer devcId);
}
