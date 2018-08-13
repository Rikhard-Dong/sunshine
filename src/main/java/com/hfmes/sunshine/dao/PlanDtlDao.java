package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.PlanDtl;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 10:39
 */
public interface PlanDtlDao {

    /**
     * 根据plan dtl id 查询
     *
     * @param planDtlId plan dtl id
     * @return 返回planDtl
     */
    PlanDtl findById(Integer planDtlId);
}
