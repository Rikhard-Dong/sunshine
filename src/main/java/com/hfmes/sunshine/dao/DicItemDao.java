package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.DicItem;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:35
 */
public interface DicItemDao {
    DicItem findById(Integer id);

    DicItem findByCode(String code);
}
