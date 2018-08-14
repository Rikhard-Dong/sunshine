package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.SCMethod;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 18:56
 */
public interface SCMethodDao {

    /**
     * @param opId
     * @return
     */
    List<SCMethod> findByOpId(Integer opId);

    /**
     * 获取所有操作
     *
     * @return
     */
    List<SCMethod> findAll();
}
