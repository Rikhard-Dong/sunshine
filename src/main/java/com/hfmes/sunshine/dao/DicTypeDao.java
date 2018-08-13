package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.DicType;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:29
 */
public interface DicTypeDao {
    /**
     * 根据type id查询
     *
     * @param id type id
     * @return DicType
     */
    DicType findById(Integer id);

    /**
     * 根据code查询
     *
     * @param code code
     * @return DicType
     */
    DicType findByCode(String code);
}
