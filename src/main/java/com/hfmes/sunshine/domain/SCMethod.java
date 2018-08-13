package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 18:55
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class SCMethod {
    private Integer scMethodId;
    private String methodName;
    private String desc;
    private String type;
    private String servicesName;
    private String rtnType;
    private String parms;
}
