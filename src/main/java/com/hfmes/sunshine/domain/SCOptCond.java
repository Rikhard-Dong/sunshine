package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 10:49
 * option和condition表的连接表
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class SCOptCond {
    private Integer scOptCondId;
    private Integer idx;
    private Integer scOptionId;
    private Integer scConditionId;
    private Boolean value;
    private String notMatch;
}
