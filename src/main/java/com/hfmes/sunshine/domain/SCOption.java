package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:14
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class SCOption {
    private Integer scOptionId;
    private Integer roleId;
    private String optName;


    // 级联属性
    @JsonIgnore
    private List<SCMethod> scMethods;
    @JsonIgnore
    private List<SCCondition> scConditions;
}
