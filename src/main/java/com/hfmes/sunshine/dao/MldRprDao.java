package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.MldRpr;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/15 7:10
 */
public interface MldRprDao {

    /* ****************************************
     * insert
     ******************************************/

    /**
     * @param devRpr
     * @return
     */
    Integer insertOne(MldRpr devRpr);

    /* ****************************************
     * update
     ******************************************/

    /**
     * @param mldRprId
     * @param repairerId
     * @param rprName
     * @param startTime
     * @return
     */
    Integer updateRepairer(@Param("mldRprId") Integer mldRprId,
                           @Param("rprId") Integer repairerId,
                           @Param("rprName") String rprName,
                           @Param("startTime") Date startTime);

    /**
     * @param mldRprId
     * @param endTime
     * @return
     */
    Integer updateCompleteRepair(@Param("mldRprId") Integer mldRprId,
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
    List<MldRpr> findUnComplete();

    /**
     * 查找最新一条模具维修信息, 根据模具id
     *
     * @param mldDtlId
     * @return
     */
    MldRpr findByMldIdTop1(Integer mldDtlId);
}
