package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:28
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class DicType {
    private Integer dicTypeId;
    private String title;
    private String code;
    private String memo;
}
