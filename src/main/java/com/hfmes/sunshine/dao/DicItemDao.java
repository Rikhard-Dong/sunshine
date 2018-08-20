package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.DicItem;

import java.util.List;
import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:35
 */
public interface DicItemDao {

    /**
     * 根据item id查询item
     *
     * @param id item id
     * @return DicItem
     */
    DicItem findById(Integer id);

    /**
     * 根据code 查询
     *
     * @param code code
     * @return DicItem
     */
    DicItem findByCode(String code);

    /**
     * 获取 设备 模具 工单对应的状态
     * @return
     */
    List<Map<String,String>>findStausByCode();
}
