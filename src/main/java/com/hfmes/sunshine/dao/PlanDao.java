package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Plan;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:11
 */
public interface PlanDao {
    Plan findByPlanId(Integer planId);
}
