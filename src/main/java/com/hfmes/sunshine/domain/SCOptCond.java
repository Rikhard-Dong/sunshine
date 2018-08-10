package com.hfmes.sunshine.domain;

import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 10:49
 * option和condition表的连接表
 */
@Data
public class SCOptCond {
    private Integer scOptCondId;
    private Integer idx;
    private Integer scOptionId;
    private Integer scConditionId;
    private Boolean value;
}
