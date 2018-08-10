package com.hfmes.sunshine.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 10:50
 */
public interface SCOptCondDao {

    Boolean getValueByOptIdAndConditionId(@Param("optId") Integer optId,
                                          @Param("conditionId") Integer conditionId);
}
