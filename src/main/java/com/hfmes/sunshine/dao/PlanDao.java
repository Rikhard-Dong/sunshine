package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Plan;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:11
 */
public interface PlanDao {
    /**
     * 根据plan id查询
     *
     * @param planId plan id
     * @return plan
     */
    Plan findByPlanId(Integer planId);
}
