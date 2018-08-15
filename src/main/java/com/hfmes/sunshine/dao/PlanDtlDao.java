package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.PlanDtl;
import org.apache.ibatis.annotations.Param;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 10:39
 */
public interface PlanDtlDao {

    /* ****************************************
     * update
     ******************************************/

    /**
     * 更新完成数量
     *
     * @param planDtlId
     * @param cmpNum
     * @return
     */
    Integer updateCmpNum(@Param("planDtlId") Integer planDtlId,
                         @Param("cmpNum") Integer cmpNum);


    /**
     * 更新数量以及设置状态为完成
     *
     * @param planDtlId
     * @param cmpNum
     * @return
     */
    Integer updateCmpNumAndComplete(@Param("planDtlId") Integer planDtlId,
                                    @Param("cmpNum") Integer cmpNum);

    /* ****************************************
     * Select
     ******************************************/

    /**
     * 根据plan dtl id 查询
     *
     * @param planDtlId plan dtl id
     * @return 返回planDtl
     */
    PlanDtl findById(Integer planDtlId);
}
