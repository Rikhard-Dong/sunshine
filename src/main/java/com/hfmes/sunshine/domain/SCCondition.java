package com.hfmes.sunshine.domain;

import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 18:33
 */
@Data
public class SCCondition {
    private Integer scConditionId;
    private String condName;
    private String desc;
    private String type;
    private String servicesName;
    private String rtnType;
    private String parms;
}
