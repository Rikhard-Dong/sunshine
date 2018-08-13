package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.MatType;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 13:26
 */
public interface MatTypeDao {

    /**
     * 根据mat type id获取对象
     *
     * @param matTypeId mat type id
     * @return matType
     */
    MatType findById(Integer matTypeId);
}
