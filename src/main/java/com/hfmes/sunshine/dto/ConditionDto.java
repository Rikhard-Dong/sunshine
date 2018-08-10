package com.hfmes.sunshine.dto;

import com.hfmes.sunshine.domain.SCCondition;
import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 10:45
 */
@Data
public class ConditionDto {
    private Integer scConditionId;
    private String condName;
    private String desc;
    private String type;
    private String servicesName;
    private String rtnType;
    private String parms;
    private Boolean value;

    public ConditionDto() {
    }

    public ConditionDto(SCCondition condition, Boolean value) {
        this.scConditionId = condition.getScConditionId();
        this.condName = condition.getCondName();
        this.desc = condition.getDesc();
        this.type = condition.getType();
        this.servicesName = condition.getServicesName();
        this.rtnType = condition.getRtnType();
        this.parms = condition.getParms();
        this.value = value;
    }
}
