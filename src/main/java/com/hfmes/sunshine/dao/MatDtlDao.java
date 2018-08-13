package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.MatDtl;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 13:28
 */
public interface MatDtlDao {

    /**
     * 根据mat dtl id获取对象
     *
     * @param matDtlId mat dtl id
     * @return matDtl
     */
    MatDtl findById(Integer matDtlId);
}
