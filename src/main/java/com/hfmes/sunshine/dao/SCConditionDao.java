package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.SCCondition;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 18:35
 */
public interface SCConditionDao {

    /**
     * 根据操作id查询出对应的条件
     *
     * @param opId 操作id
     * @return list of condition
     */
    List<SCCondition> findByOpId(Integer opId);
}
