package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:14
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class SCOption {
    private Integer scOptionId;
    private Integer roleId;
    private String optName;
}
