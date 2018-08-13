package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 13:25
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class MatType {
    private Integer matTypeId;
    private String title;
    private String type;
    private String spec;
    private String memo;
}
