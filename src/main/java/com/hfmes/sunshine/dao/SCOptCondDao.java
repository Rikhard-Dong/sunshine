package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.SCOptCond;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Param;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 10:50
 */
public interface SCOptCondDao {

    Boolean getValueByOptIdAndConditionId(@Param("optId") Integer optId,
                                          @Param("conditionId") Integer conditionId);

    /**
     * 根据操作id和条件id和值查询出一条optCond记录
     *
     * @param opId
     * @param condId
     * @param value
     * @return
     */
    SCOptCond findByOptionIdAndCondIdAndValue(@Param("opId") Integer opId,
                                              @Param("condId") Integer condId,
                                              @Param("value") Boolean value);
}
